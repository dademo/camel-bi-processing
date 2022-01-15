/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import lombok.SneakyThrows;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Optional;

import static fr.dademo.batch.beans.BeanValues.BATCH_DATASOURCE_NAME;
import static fr.dademo.batch.beans.BeanValues.CONFIG_JOBS_REPOSITORY_ENABLED;

/**
 * @author dademo
 */
@Configuration
public class BatchRepository {

    @Bean
    @ConditionalOnProperty(
        value = CONFIG_JOBS_REPOSITORY_ENABLED,
        havingValue = "true"
    )
    @SneakyThrows
    public JobRepository jdbcJobRepository(DataSourcesFactory dataSourcesFactory,
                                           PlatformTransactionManager transactionManager,
                                           BatchConfiguration batchConfiguration) {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSourcesFactory.getDataSource(BATCH_DATASOURCE_NAME));
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix(
            Optional.ofNullable(batchConfiguration.getRepository().getTablePrefix())
                .orElse(BatchConfiguration.BatchRepositoryConfiguration.getDefaultTablePrefix())
        );
        factory.setMaxVarCharLength(1000);
        return factory.getObject();
    }
}
