package fr.dademo.bi.companies.tools;

import io.quarkus.arc.DefaultBean;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.jeasy.batch.core.job.JobExecutor;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;

public class DefaultAppBeans {

    @ConfigProperty(name = "http.connectTimeoutSeconds", defaultValue = "5")
    Long connectTimeoutSeconds;

    @ConfigProperty(name = "http.callReadTimeoutSeconds", defaultValue = "10")
    Long callReadTimeoutSeconds;

    @ConfigProperty(name = "http.callTimeoutSeconds", defaultValue = "0")
    Long callTimeoutSeconds;

    @DefaultBean
    @ApplicationScoped
    public OkHttpClient okHttpClient() {

        var loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Duration.ofSeconds(connectTimeoutSeconds))
                .readTimeout(Duration.ofSeconds(callReadTimeoutSeconds))
                .callTimeout(Duration.ofSeconds(callTimeoutSeconds))
                .build();
    }

    @DefaultBean
    @ApplicationScoped
    public JobExecutor jobExecutor(ManagedExecutor managedExecutor) {
        //return new JobExecutor(managedExecutor);
        return new JobExecutor();
    }
}
