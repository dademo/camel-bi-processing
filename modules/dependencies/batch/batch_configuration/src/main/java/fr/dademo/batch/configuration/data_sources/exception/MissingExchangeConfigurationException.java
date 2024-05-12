/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources.exception;

import java.io.Serial;
import java.util.function.Supplier;

/**
 * @author dademo
 */
public class MissingExchangeConfigurationException extends MissingConfigurationException {

    @Serial
    private static final long serialVersionUID = -7649089570511038630L;

    private static final String ELEMENT_NAME = "exchange";

    public MissingExchangeConfigurationException(String missingKey) {
        super(ELEMENT_NAME, missingKey);
    }

    public static Supplier<MissingQueueConfigurationException> of(String missingKey) {
        return () -> new MissingQueueConfigurationException(missingKey);
    }
}
