package fr.dademo.bi.companies.tools.batch.job;

import org.springframework.batch.core.Job;

public interface BatchJobProvider {

    Job getJob();
}
