package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.tools.DatabaseSQLDialectProvider;
import org.jooq.DSLContext;
import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static fr.dademo.bi.companies.beans.BeanValues.*;

@Configuration
public class DSLContexts {

    @Bean(STG_DSL_CONTEXT_DIALECT_PROVIDER)
    @ConditionalOnProperty(
            value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME,
            havingValue = "true"
    )
    public DatabaseSQLDialectProvider stgSqlDialectProvider(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DatabaseSQLDialectProvider(dataSource);
    }

    @Bean(STG_DSL_CONTEXT)
    @ConditionalOnProperty(
            value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME,
            havingValue = "true"
    )
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
}
