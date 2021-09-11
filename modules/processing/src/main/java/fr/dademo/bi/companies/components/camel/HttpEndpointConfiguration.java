package fr.dademo.bi.companies.components.camel;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface HttpEndpointConfiguration {

    String getUrl();

    Long getConnectTimeoutSecond();

    Long getCallTimeoutSecond();

    String getQueryParameters();

    Boolean getUseLocalCache();

    Long getCacheExpirationSeconds();

    Optional<Duration> getCacheExpiration();

    List<String> getFileHashUrls();
}
