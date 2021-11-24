/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

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

/**
 * @author dademo
 */
@Configuration
public class DSLContexts {

    @Bean(BATCH_DATASOURCE_DIALECT_PROVIDER_BEAN_NAME)
    @ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + BATCH_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
    )
    public DatabaseSQLDialectProvider batchSqlDialectProvider(@Qualifier(BATCH_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DatabaseSQLDialectProvider(dataSource);
    }

    @Bean(STG_DATASOURCE_DIALECT_PROVIDER_BEAN_NAME)
    @ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
    )
    public DatabaseSQLDialectProvider stgSqlDialectProvider(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource) {
        return new DatabaseSQLDialectProvider(dataSource);
    }

    @Bean(STG_DATASOURCE_DSL_CONTEXT_BEAN_NAME)
    @ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
    )
    public DSLContext stgDslContext(@Qualifier(STG_DATASOURCE_BEAN_NAME) DataSource dataSource,
                                    @Qualifier(STG_DATASOURCE_DIALECT_PROVIDER_BEAN_NAME) DatabaseSQLDialectProvider sqlDialectProvider) {

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
