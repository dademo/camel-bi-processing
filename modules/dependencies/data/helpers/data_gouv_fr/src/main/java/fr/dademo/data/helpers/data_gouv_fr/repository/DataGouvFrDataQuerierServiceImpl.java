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
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

@SuppressWarnings("java:S1075")
@Service
public class DataGouvFrDataQuerierServiceImpl extends BaseDataGouvFrDataQuerierServiceImpl implements DataGouvFrDataQuerierService {

    private static final String DATA_SET_URL_SCHEME = "https";
    private static final String DATA_SET_URL_HOST = "www.data.gouv.fr";
    private static final String DATA_SET_URL_PATH = "api/1/datasets/";

    @Autowired
    private HttpDataQuerierRepository httpDataQuerierRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Nonnull
    public DataGouvFrDataSetPage listDataSets(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) throws IOException {

        return mapper.readValue(
                httpDataQuerierRepository.basicQuery(getInputStreamIdentifierForDataSetQuery(queryOptions)),
                DataGouvFrDataSetPage.class
        );
    }

    @Override
    @Nonnull
    public DataGouvFrDataSet getDataSet(@Nonnull String dataSetTitle) throws IOException {

        try {
            return mapper.readValue(
                    httpDataQuerierRepository.basicQuery(getInputStreamIdentifierForDataSetGet(dataSetTitle)),
                    DataGouvFrDataSet.class
            );
        } catch (FailedQueryException e) {
            if (e.getQueryResponse().code() == HTTP_NOT_FOUND) {
                throw new DataSetNotFoundException(dataSetTitle, e);
            } else {
                throw e;
            }
        }
    }

    @Nonnull
    @Override
    public InputStream queryForStream(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource,
                                      @Nonnull List<? extends DataGouvFrInputStreamValidator> inputStreamIdentifierValidators) throws IOException {

        return httpDataQuerierRepository.basicQuery(
                getInputStreamIdentifierForDataSetResource(dataGouvFrDataSetResource),
                Collections.emptyList(),
                null,
                Stream.concat(
                        DataGouvFrInputStreamValidator.of(dataGouvFrDataSetResource).stream(),
                        inputStreamIdentifierValidators.stream()
                ).collect(Collectors.toList())
        );
    }

    @Nonnull
    @SneakyThrows
    protected URL buildQueryUsingOptions(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        final var urlBuilder = new HttpUrl.Builder()
                .scheme(DATA_SET_URL_SCHEME)
                .host(DATA_SET_URL_HOST)
                .addPathSegment(DATA_SET_URL_PATH);

        Optional.ofNullable(queryOptions).ifPresent(options -> options.applyParametersToUrlBuilder(urlBuilder));

        return urlBuilder.build().url();
    }

    @Nonnull
    @SneakyThrows
    protected URL buildQueryForDataSet(@Nonnull String dataSetTitle) {

        return new HttpUrl.Builder()
                .scheme(DATA_SET_URL_SCHEME)
                .host(DATA_SET_URL_HOST)
                .addPathSegments(DATA_SET_URL_PATH)
                .addPathSegment(dataSetTitle)
                .build()
                .url();
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
                .url(new URL(dataGouvFrDataSetResource.getUrl()))
                .method("GET")
                .build();
    }
}
