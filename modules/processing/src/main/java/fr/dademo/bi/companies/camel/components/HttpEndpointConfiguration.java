package fr.dademo.bi.companies.camel.components;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface HttpEndpointConfiguration {

    String getUrl();

    Long getConnectTimeoutSeconds();

    Long getCallReadTimeoutSeconds();

    Long getCallTimeoutSeconds();

    String getQueryParameters();

    Boolean getUseLocalCache();

    Long getCacheExpirationSeconds();

    Optional<Duration> getCacheExpiration();

    List<String> getFileHashUrls();
}
