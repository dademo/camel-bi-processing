package fr.dademo.bi.companies.configuration.exceptions;

import java.util.function.Supplier;

public class MissingJobConfigurationException extends RuntimeException {

    public MissingJobConfigurationException(String jobName) {
        super(String.format("Missing job configuration for `%s`", jobName));
    }

    public static Supplier<MissingJobConfigurationException> ofJob(String jobName) {
        return () -> new MissingJobConfigurationException(jobName);
    }
}
