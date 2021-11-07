package fr.dademo.tools.http.beans;

import fr.dademo.tools.stream_definitions.configuration.HttpConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DefaultBeans {

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
}
