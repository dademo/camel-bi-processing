package fr.dademo.bi.companies.tools.batch.job_configuration;

import org.jberet.job.model.Job;

public interface JobProvider {

    Job getJob();
}
