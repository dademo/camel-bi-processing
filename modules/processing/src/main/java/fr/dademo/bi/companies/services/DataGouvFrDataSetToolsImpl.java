package fr.dademo.bi.companies.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.services.datamodel.GouvFrDataSetDefinition;
import fr.dademo.bi.companies.services.datamodel.GouvFrDataSetResourceChecksumDefinition;
import fr.dademo.bi.companies.services.datamodel.GouvFrDataSetResourceDefinition;
import fr.dademo.bi.companies.services.exceptions.InvalidResponseException;
import fr.dademo.bi.companies.services.exceptions.MissingLocationHeaderException;
import fr.dademo.bi.companies.services.exceptions.ResourceNotFoundException;
import fr.dademo.bi.companies.services.exceptions.TooManyRedirectException;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
@Default
public class DataGouvFrDataSetToolsImpl implements DataGouvFrDataSetTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataGouvFrDataSetToolsImpl.class);

    private static final String BASE_DATASET_API_URL = "https://www.data.gouv.fr/api/1/datasets";
    private static final String HEADER_LOCATION = "Location";
    private static final int MAX_REDIRECT_COUNT = 5;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Map<String, String> WELL_KNOWN_ALGORITHMS = Map.ofEntries(
            Map.entry("SHA1", "SHA-1"),
            Map.entry("SHA256", "SHA-256"),
            Map.entry("SHA384", "SHA-384"),
            Map.entry("SHA512", "SHA-512")
    );

    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    @SneakyThrows
    public Optional<GouvFrDataSetDefinition> getDataSetDefinition(@Nonnull String dataSetName) {

        return getQueryFollowsRedirect(
                new URL(String.format("%s/%s/", BASE_DATASET_API_URL, dataSetName)),
                0,
                this::parseHttpResponse
        );
    }

    @Override
    public Optional<HashDefinition> hashDefinitionOfDataSetResourceByTitle(@Nonnull String dataSetName,
                                                                           @Nonnull String resourceTitle) {

        LOGGER.debug("Getting data definition of resource `{}` for dataset `{}`", resourceTitle, dataSetName);

        return getDataSetDefinition(dataSetName)
                .map(dataSetResourceDefinitions -> hashDefinitionByTitle(
                        dataSetResourceDefinitions,
                        dataSetName,
                        resourceTitle
                ))
                .orElseThrow(() -> ResourceNotFoundException.notFoundDataSet(dataSetName));
    }

    @Override
    public Optional<HashDefinition> hashDefinitionOfDataSetResourceByUrl(@Nonnull String dataSetName,
                                                                         @Nonnull String resourceUrl,
                                                                         boolean compareUrlQuery) {

        LOGGER.debug("Getting data definition of resource at url `{}` for dataset `{}`", resourceUrl, dataSetName);

        return getDataSetDefinition(dataSetName)
                .map(dataSetResourceDefinitions -> hashDefinitionByUrl(
                        dataSetResourceDefinitions,
                        dataSetName,
                        resourceUrl,
                        compareUrlQuery
                ))
                .orElseThrow(() -> ResourceNotFoundException.notFoundDataSet(dataSetName));
    }

    private Optional<HashDefinition> hashDefinitionByTitle(@Nonnull GouvFrDataSetDefinition dataSetDefinition,
                                                           @Nonnull String dataSetName,
                                                           @Nonnull String resourceTitle) {

        return Optional.ofNullable(filteredDataSetResourceChecksumDefinition(
                                dataSetDefinition,
                                dataSetResourceDefinition -> resourceTitle.equals(dataSetDefinition.getTitle())
                        )
                                .orElseThrow(() -> ResourceNotFoundException.notFoundByResourceTitle(dataSetName, resourceTitle))
                                .getChecksum()

                )
                .map(this::toHashDefinition);
    }

    private Optional<HashDefinition> hashDefinitionByUrl(@Nonnull GouvFrDataSetDefinition dataSetDefinition,
                                                         @Nonnull String dataSetName,
                                                         @Nonnull String resourceUrl,
                                                         boolean compareUrlQuery) {

        return Optional.ofNullable(filteredDataSetResourceChecksumDefinition(
                                dataSetDefinition,
                                dataSetResourceDefinition -> urlCompare(resourceUrl, dataSetResourceDefinition.getUrl(), compareUrlQuery)
                        )
                                .orElseThrow(() -> ResourceNotFoundException.notFoundByResourceUrl(dataSetName, resourceUrl))
                                .getChecksum()
                )
                .map(this::toHashDefinition);
    }

    @SneakyThrows
    private boolean urlCompare(String a, String b, boolean compareUrlQuery) {

        final var aUrl = new URL(a);
        final var bUrl = new URL(b);

        return Stream.of(
                        Objects.equals(aUrl.getProtocol(), bUrl.getProtocol()),
                        Objects.equals(aUrl.getHost(), bUrl.getHost()),
                        Objects.equals(aUrl.getPort(), bUrl.getPort()),
                        pathCompare(aUrl.getPath(), bUrl.getPath()),
                        !compareUrlQuery || Objects.equals(aUrl.getQuery(), bUrl.getQuery())
                ).
                allMatch(Boolean.TRUE::equals);
    }

    private boolean pathCompare(String a, String b) {

        return Objects.equals(
                Paths.get(a.replace("//", "/")).normalize(),
                Paths.get(b.replace("//", "/")).normalize()
        );
    }

    private Optional<GouvFrDataSetResourceDefinition> filteredDataSetResourceChecksumDefinition(
            GouvFrDataSetDefinition dataSetDefinition,
            Predicate<GouvFrDataSetResourceDefinition> predicate) {

        return dataSetDefinition.getResources().stream()
                .filter(predicate)
                .findFirst();
    }

    private HashDefinition toHashDefinition(GouvFrDataSetResourceChecksumDefinition dataSetResourceChecksumDefinition) {

        return HashDefinition.of(
                dataSetResourceChecksumDefinition.getValue(),
                normalizedHashAlgorithmName(dataSetResourceChecksumDefinition.getType())
        );
    }

    private String normalizedHashAlgorithmName(String original) {

        return WELL_KNOWN_ALGORITHMS.entrySet().stream()
                .filter(def -> original.equalsIgnoreCase(def.getKey()))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(original);
    }

    @SneakyThrows
    private <T> Optional<T> getQueryFollowsRedirect(@Nonnull URL queryUrl,
                                                    int redirectCount,
                                                    Function<InputStream, T> responseBodyMapper) {

        if (redirectCount > MAX_REDIRECT_COUNT) {
            throw new TooManyRedirectException();
        }

        final var request = new Request.Builder()
                .url(queryUrl)
                .get()
                .build();

        try (final var response = okHttpClient.newCall(request).execute()) {

            if (response.isSuccessful()) {
                return Optional.ofNullable(response.body())
                        .map(ResponseBody::byteStream)
                        .map(responseBodyMapper);
            }

            if (response.isRedirect()) {
                return getQueryFollowsRedirect(
                        Optional.ofNullable(request.header(HEADER_LOCATION))
                                .map(newLocation -> updateUrl(queryUrl, newLocation))
                                .orElseThrow(MissingLocationHeaderException::new),
                        redirectCount + 1,
                        responseBodyMapper
                );
            }

            throw new InvalidResponseException(response.code());
        }
    }

    @SneakyThrows
    private URL updateUrl(URL original, String appendedPath) {

        try {
            return new URL(appendedPath);
        } catch (MalformedURLException e) {
            return new URL(
                    original.getProtocol(),
                    original.getHost(),
                    original.getPort(),
                    normalizedPath(original.getPath(), appendedPath)
            );
        }
    }

    private String normalizedPath(String originalPath, String appendedPath) {

        // Absolute path, it will override the original path
        if (appendedPath.startsWith("/")) {
            return appendedPath;
        } else {
            final var sb = new StringBuilder(originalPath);
            if (originalPath.endsWith("/")) {
                sb.append("/");
            }
            sb.append(appendedPath);
            return sb.toString();
        }
    }

    @SneakyThrows
    private GouvFrDataSetDefinition parseHttpResponse(InputStream in) {
        return MAPPER.readValue(in, GouvFrDataSetDefinition.class);
    }
}
