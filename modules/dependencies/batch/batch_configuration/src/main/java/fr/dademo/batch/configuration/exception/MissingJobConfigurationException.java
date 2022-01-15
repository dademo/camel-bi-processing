/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.exception;

import java.util.function.Supplier;

/**
 * @author dademo
 */
public class MissingJobConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -7249085560642763180L;

    public MissingJobConfigurationException(String jobName) {
        super(String.format("Missing job configuration for `%s`", jobName));
    }

    public static Supplier<MissingJobConfigurationException> ofJob(String jobName) {
        return () -> new MissingJobConfigurationException(jobName);
    }
}
