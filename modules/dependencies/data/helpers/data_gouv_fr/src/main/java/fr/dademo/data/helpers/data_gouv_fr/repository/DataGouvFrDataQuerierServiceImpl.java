/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSetPage;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.http.validators.DataGouvFrInputStreamValidator;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.DataSetNotFoundException;
import fr.dademo.data.helpers.data_gouv_fr.repository.query.DataGouvFrMetadataServiceMetadataQueryOptions;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.reader.http.repository.exception.FailedQueryException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static fr.dademo.reader.http.helpers.URIHelper.updateURIWithParameters;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

/**
 * @author dademo
 */
@Service
public class DataGouvFrDataQuerierServiceImpl extends BaseDataGouvFrDataQuerierServiceImpl implements DataGouvFrDataQuerierService {

    private static final String DATA_SET_URL_SCHEME = "https";
    private static final String DATA_SET_URL_HOST = "www.data.gouv.fr";
    private static final String DATA_SET_URL_PATH = "api/1/datasets/";

    private final HttpDataQuerierRepository httpDataQuerierRepository;
    private final ObjectMapper mapper;

    public DataGouvFrDataQuerierServiceImpl(@Nonnull HttpDataQuerierRepository httpDataQuerierRepository,
                                            @Nonnull ObjectMapper mapper) {
        this.httpDataQuerierRepository = httpDataQuerierRepository;
        this.mapper = mapper;
    }

    @Override
    @Nonnull
    public DataGouvFrDataSetPage listDataSets(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) throws IOException, InterruptedException {

        return mapper.readValue(
            httpDataQuerierRepository.basicQuery(getInputStreamIdentifierForDataSetQuery(queryOptions)),
            DataGouvFrDataSetPage.class
        );
    }

    @Override
    @Nonnull
    public DataGouvFrDataSet getDataSet(@Nonnull String dataSetTitle) throws IOException, InterruptedException {

        try {
            return mapper.readValue(
                httpDataQuerierRepository.basicQuery(getInputStreamIdentifierForDataSetGet(dataSetTitle)),
                DataGouvFrDataSet.class
            );
        } catch (FailedQueryException e) {
            if (e.getQueryResponse().statusCode() == HTTP_NOT_FOUND) {
                throw new DataSetNotFoundException(dataSetTitle, e);
            } else {
                throw e;
            }
        }
    }

    @Nonnull
    @Override
    public InputStream queryForStream(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource,
                                      @Nonnull List<? extends DataGouvFrInputStreamValidator> inputStreamIdentifierValidators) throws IOException, InterruptedException {

        return httpDataQuerierRepository.basicQuery(
            getInputStreamIdentifierForDataSetResource(dataGouvFrDataSetResource),
            Collections.emptyList(),
            null,
            Stream.concat(
                DataGouvFrInputStreamValidator.of(dataGouvFrDataSetResource).stream(),
                inputStreamIdentifierValidators.stream()
            ).toList()
        );
    }

    @Nonnull
    @SneakyThrows({URISyntaxException.class, MalformedURLException.class})
    protected URL buildQueryUsingOptions(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        final var baseURI = new URI(DATA_SET_URL_SCHEME, DATA_SET_URL_HOST, DATA_SET_URL_PATH, "");
        return Optional.ofNullable(queryOptions)
            .map(opts -> updateURIWithParameters(baseURI, opts.getUrlParams()))
            .orElse(baseURI)
            .toURL();
    }

    @Nonnull
    @SneakyThrows({URISyntaxException.class, MalformedURLException.class})
    protected URL buildQueryForDataSet(@Nonnull String dataSetTitle) {
        return new URI(
            DATA_SET_URL_SCHEME,
            DATA_SET_URL_HOST,
            DATA_SET_URL_PATH + dataSetTitle,
            ""
        ).toURL();
    }

    @Nonnull
    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetQuery(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        return HttpInputStreamIdentifier.builder()
            .url(buildQueryUsingOptions(queryOptions))
            .method("GET")
            .build();
    }

    @Nonnull
    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetGet(@Nonnull String dataSetTitle) {

        return HttpInputStreamIdentifier.builder()
            .url(buildQueryForDataSet(dataSetTitle))
            .method("GET")
            .build();
    }

    @SneakyThrows
    @Nonnull
    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetResource(DataGouvFrDataSetResource dataGouvFrDataSetResource) {

        return HttpInputStreamIdentifier.builder()
            .url(new URI(dataGouvFrDataSetResource.getUrl()).toURL())
            .contentType(dataGouvFrDataSetResource.getMime())
            .method("GET")
            .build();
    }
}
