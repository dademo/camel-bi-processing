package fr.dademo.bi.companies.configuration.exception;

import java.util.function.Supplier;

public class MissingJobConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -7249085560642763180L;

    public MissingJobConfigurationException(String jobName) {
        super(String.format("Missing job configuration for `%s`", jobName));
    }

    public static Supplier<MissingJobConfigurationException> ofJob(String jobName) {
        return () -> new MissingJobConfigurationException(jobName);
    }
}
