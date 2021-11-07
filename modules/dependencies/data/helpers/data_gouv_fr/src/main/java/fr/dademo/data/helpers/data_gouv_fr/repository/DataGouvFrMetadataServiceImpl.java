package fr.dademo.data.helpers.data_gouv_fr.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSetPage;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.http.validators.DataGouvFrInputStreamValidator;
import fr.dademo.data.helpers.data_gouv_fr.repository.query.DataGouvFrMetadataServiceMetadataQueryOptions;
import fr.dademo.tools.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.http.repository.HttpDataQuerier;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

@SuppressWarnings("java:S1075")
@Service
public class DataGouvFrMetadataServiceImpl implements DataGouvFrMetadataService {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATA_SET_URL_SCHEME = "https";
    private static final String DATA_SET_URL_HOST = "www.data.gouv.fr";
    private static final String DATA_SET_URL_PATH = "/api/1/datasets/";

    @Autowired
    private HttpDataQuerier httpDataQuerier;

    @Override
    @SneakyThrows
    public DataGouvFrDataSetPage listDataSets(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        return MAPPER.readValue(
                httpDataQuerier.basicQuery(getInputStreamIdentifierForDataSetQuery(queryOptions)),
                DataGouvFrDataSetPage.class
        );
    }

    @Override
    @SneakyThrows
    public DataGouvFrDataSet getDataSet(@Nonnull String dataSetName) {

        return MAPPER.readValue(
                httpDataQuerier.basicQuery(getInputStreamIdentifierForDataSetGet(dataSetName)),
                DataGouvFrDataSet.class
        );
    }

    @Override
    public InputStream queryForStream(DataGouvFrDataSetResource dataGouvFrDataSetResource) {

        return httpDataQuerier.basicQuery(
                getInputStreamIdentifierForDataSetResource(dataGouvFrDataSetResource),
                Collections.emptyList(),
                null,
                DataGouvFrInputStreamValidator.of(dataGouvFrDataSetResource)
                        .map(Collections::singletonList)
                        .orElse(Collections.emptyList())
        );
    }

    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetQuery(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        return HttpInputStreamIdentifier.builder()
                .url(buildQueryUsingOptions(queryOptions))
                .method("GET")
                .build();
    }

    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetGet(String dataSetName) {

        return HttpInputStreamIdentifier.builder()
                .url(buildQueryForDataSet(dataSetName))
                .method("GET")
                .build();
    }

    @SneakyThrows
    private HttpInputStreamIdentifier getInputStreamIdentifierForDataSetResource(DataGouvFrDataSetResource dataGouvFrDataSetResource) {

        return HttpInputStreamIdentifier.builder()
                .url(new URL(dataGouvFrDataSetResource.getUrl()))
                .method("GET")
                .build();
    }

    @SneakyThrows
    private URL buildQueryUsingOptions(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) {

        final var urlBuilder = new HttpUrl.Builder()
                .scheme(DATA_SET_URL_SCHEME)
                .host(DATA_SET_URL_HOST)
                .addPathSegment(DATA_SET_URL_PATH);

        Optional.ofNullable(queryOptions).ifPresent(options -> options.applyParametersToUrlBuilder(urlBuilder));

        return urlBuilder.build().url();
    }

    @SneakyThrows
    private URL buildQueryForDataSet(String dataSetName) {

        return new HttpUrl.Builder()
                .scheme(DATA_SET_URL_SCHEME)
                .host(DATA_SET_URL_HOST)
                .addPathSegment(DATA_SET_URL_PATH)
                .addPathSegment(dataSetName)
                .build()
                .url();
    }

}
