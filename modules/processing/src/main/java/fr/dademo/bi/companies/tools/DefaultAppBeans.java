package fr.dademo.bi.companies.tools;

import fr.dademo.bi.companies.configuration.HttpConfiguration;
import fr.dademo.bi.companies.tools.batch.writer.NoActionBatchWriter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.impl.DSL;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Default;

import javax.sql.DataSource;
import java.time.Duration;

import static fr.dademo.bi.companies.beans.DataSources.STG_DATASOURCE_BEAN_NAME;


public class DefaultAppBeans {

    public static final String STG_DSL_CONTEXT = "stgDslContext";
    public static final String STG_DSL_CONTEXT_DIALECT_PROVIDER = STG_DSL_CONTEXT + "ContextProvider";

    @Autowired
    private HttpConfiguration httpConfiguration;

    @Bean
    @Qualifier(STG_DSL_CONTEXT_DIALECT_PROVIDER)
    public DatabaseSQLDialectProvider stgSqlDialectProvider(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DatabaseSQLDialectProvider(dataSource);
    }

    @Bean
    @Qualifier(STG_DSL_CONTEXT)
    public DSLContext stgDslContext(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource,
                                    @Qualifier(STG_DSL_CONTEXT_DIALECT_PROVIDER) DatabaseSQLDialectProvider sqlDialectProvider) {

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

    @Bean
    @Default
    private ItemWriter<?> defaultItemWriter() {
        return NoActionBatchWriter.INSTANCE;
    }

    @Default
    @Bean
    public OkHttpClient okHttpClient() {

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
