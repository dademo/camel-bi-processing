package fr.dademo.bi.companies.tools;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.arc.DefaultBean;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jeasy.batch.core.job.JobExecutor;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.impl.DSL;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.time.Duration;

import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.PERSISTENCE_UNIT_NAME;

public class DefaultAppBeans {

    public static final String STG_DSL_CONTEXT = "stgDslContext";
    public static final String STG_DSL_CONTEXT_DIALECT_PROVIDER = STG_DSL_CONTEXT + "contextProvider";

    @ConfigProperty(name = "http.connectTimeoutSeconds", defaultValue = "5")
    Long connectTimeoutSeconds;

    @ConfigProperty(name = "http.callReadTimeoutSeconds", defaultValue = "10")
    Long callReadTimeoutSeconds;

    @ConfigProperty(name = "http.callTimeoutSeconds", defaultValue = "0")
    Long callTimeoutSeconds;

    @ApplicationScoped
    @Named(STG_DSL_CONTEXT_DIALECT_PROVIDER)
    public DatabaseSQLDialectProvider stgSqlDialectProvider(@DataSource(PERSISTENCE_UNIT_NAME) AgroalDataSource dataSource) {
        return new DatabaseSQLDialectProvider(dataSource);
    }

    @ApplicationScoped
    @Named(STG_DSL_CONTEXT)
    public DSLContext stgDslContext(@DataSource(PERSISTENCE_UNIT_NAME) AgroalDataSource dataSource,
                                    @Named(STG_DSL_CONTEXT_DIALECT_PROVIDER) DatabaseSQLDialectProvider sqlDialectProvider) {

        return DSL.using(
                dataSource,
                sqlDialectProvider.get(),
                new Settings()
                        .withStatementType(StatementType.PREPARED_STATEMENT)
                        .withAttachRecords(false)
                        .withReturnRecordToPojo(true)
                        .withExecuteLogging(false)
                        .withThrowExceptions(ThrowExceptions.THROW_ALL)
                        .withFetchWarnings(true)
        );
    }

    @DefaultBean
    @ApplicationScoped
    public OkHttpClient okHttpClient() {

        final var loggingInterceptor = new HttpLoggingInterceptor();
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
    public JobExecutor jobExecutor() {
        return new JobExecutor();
    }
}
