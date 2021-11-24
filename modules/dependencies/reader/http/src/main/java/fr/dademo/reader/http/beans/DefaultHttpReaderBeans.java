/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.beans;

import fr.dademo.data.generic.stream_definitions.configuration.HttpConfiguration;
import fr.dademo.reader.http.repository.DefaultHttpDataQuerierRepository;
import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author dademo
 */
@Configuration
public class DefaultHttpReaderBeans {

    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    public OkHttpClient defaultOkHttpClient(HttpConfiguration httpConfiguration) {

        final var loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(Duration.ofSeconds(httpConfiguration.getConnectTimeoutSeconds()))
            .readTimeout(Duration.ofSeconds(httpConfiguration.getCallReadTimeoutSeconds()))
            .callTimeout(Duration.ofSeconds(httpConfiguration.getCallTimeoutSeconds()))
            .build();
    }

    @Bean
    @ConditionalOnMissingBean(HttpDataQuerierRepository.class)
    public HttpDataQuerierRepository defaultHttpDataQuerierRepository(OkHttpClient okHttpClient) {
        return new DefaultHttpDataQuerierRepository(okHttpClient);
    }
}
