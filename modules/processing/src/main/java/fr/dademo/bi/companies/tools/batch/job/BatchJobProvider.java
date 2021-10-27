package fr.dademo.bi.companies.tools.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.context.annotation.Bean;

import javax.annotation.Nonnull;

public interface BatchJobProvider {

    @Nonnull
    @Bean
    Job getJob();
}
