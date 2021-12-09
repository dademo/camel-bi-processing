/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources.exception;

import java.util.function.Supplier;

/**
 * @author dademo
 */
public class MissingQueueConfigurationException extends MissingConfigurationException {

    private static final long serialVersionUID = 611587544600988566L;

    private static final String ELEMENT_NAME = "queue";

    public MissingQueueConfigurationException(String missingKey) {
        super(ELEMENT_NAME, missingKey);
    }

    public static Supplier<MissingQueueConfigurationException> of(String missingKey) {
        return () -> new MissingQueueConfigurationException(missingKey);
    }
}
