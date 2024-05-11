/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.beans;

import fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration;
import fr.dademo.reader.http.repository.DefaultHttpDataQuerierRepository;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Configuration
public class DefaultHttpReaderBeans {

    public static final String HTTP_CLIENT_EXECUTOR_BEAN_NAME = "httpClientExecutor";

    @Bean(HTTP_CLIENT_EXECUTOR_BEAN_NAME)
    @ConditionalOnMissingBean(name = HTTP_CLIENT_EXECUTOR_BEAN_NAME)
    public Executor httpClientExecutor(@Nonnull HttpConfiguration httpConfiguration) {

        return new ThreadPoolExecutor(
            httpConfiguration.getExecutor().getCorePoolSize(),
            httpConfiguration.getExecutor().getMaximumPoolSize(),
            httpConfiguration.getExecutor().getKeepAliveTimeSeconds(),
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
        );
    }

    @Bean
    @ConditionalOnMissingBean(HttpClient.class)
    public HttpClient defaultHttpClient(@Nonnull HttpConfiguration httpConfiguration,
                                        @Nonnull @Qualifier(HTTP_CLIENT_EXECUTOR_BEAN_NAME) Executor httpClientExecutor) {

        return HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(httpConfiguration.getConnectTimeoutSeconds()))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .executor(httpClientExecutor)
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(HttpDataQuerierRepository.class)
    public HttpDataQuerierRepository defaultHttpDataQuerierRepository(HttpClient httpClient) {
        return new DefaultHttpDataQuerierRepository(httpClient);
    }
}
