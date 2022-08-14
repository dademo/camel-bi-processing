/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.exception;

import java.util.function.Supplier;

public class MissingJobDataSourceConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 7833511717249569579L;

    public MissingJobDataSourceConfigurationException(String jobName) {
        super(String.format("Missing job data source configuration for `%s`", jobName));
    }

    public static Supplier<MissingJobDataSourceConfigurationException> forJob(String jobName) {
        return () -> new MissingJobDataSourceConfigurationException(jobName);
    }
}
