/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.beans.exception;

import fr.dademo.tools.lock.configuration.LockConfiguration;

import java.io.Serial;

/**
 * @author dademo
 */
public class MissingInfinispanConfiguration extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9222367703197865532L;

    public MissingInfinispanConfiguration() {
        super("Missing Hazelcast configuration [%s.infinispan]".formatted(LockConfiguration.CONFIGURATION_PROPERTY_PREFIX));
    }
}
