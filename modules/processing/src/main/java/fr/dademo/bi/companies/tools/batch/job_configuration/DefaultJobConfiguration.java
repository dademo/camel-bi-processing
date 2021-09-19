package fr.dademo.bi.companies.tools.batch.job_configuration;

import fr.dademo.bi.companies.tools.batch.batch_steps.BatchJobStepProvider;
import io.quarkus.arc.DefaultBean;
import org.jberet.job.model.Job;
import org.jberet.job.model.JobBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@DefaultBean
@ApplicationScoped
public class DefaultJobConfiguration implements JobProvider {

    @Inject
    OrderedJobStepsProvider orderedJobStepsProvider;

    public Job getJob() {


        var jobBuilder = new JobBuilder(getDefaultJobName(orderedJobStepsProvider));

        orderedJobStepsProvider.getAppJobStep()
                .stream()
                .map(BatchJobStepProvider::getJobStep)
                .forEach(jobBuilder::step);

        return jobBuilder.build();
    }

    private String getDefaultJobName(OrderedJobStepsProvider orderedJobStepsProvider) {

        return String.format(
                "Application job[%s]",
                orderedJobStepsProvider
                        .getAppJobStep()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(","))
        );
    }
}
