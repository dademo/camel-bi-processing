package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.configuration.HttpConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OKHttpClient {

    @Bean
    public OkHttpClient okHttpClient(HttpConfiguration httpConfiguration) {

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
