package fr.dademo.bi.companies.beans;

import fr.dademo.bi.companies.configuration.BatchConfiguration;
import lombok.SneakyThrows;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;

import static fr.dademo.bi.companies.beans.BeanValues.BATCH_DATASOURCE_NAME;
import static fr.dademo.bi.companies.beans.BeanValues.CONFIG_JOBS_REPOSITORY_ENABLED;

@Configuration
public class Batch {

    @Bean
    @ConditionalOnProperty(
            value = CONFIG_JOBS_REPOSITORY_ENABLED,
            havingValue = "true"
    )
    @SneakyThrows
    public JobRepository jdbcJobRepository(@Qualifier(BATCH_DATASOURCE_NAME) DataSource dataSource,
                                           PlatformTransactionManager transactionManager,
                                           BatchConfiguration batchConfiguration) {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setTablePrefix(
                Optional.ofNullable(batchConfiguration.getRepository().getTablePrefix())
                        .orElse(BatchConfiguration.BatchRepositoryConfiguration.getDefaultTablePrefix())
        );
        factory.setMaxVarCharLength(1000);
        return factory.getObject();
    }
}
