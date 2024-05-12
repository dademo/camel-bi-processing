/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.beans.exception;

import fr.dademo.tools.lock.configuration.LockConfiguration;

import java.io.Serial;

/**
 * @author dademo
 */
public class MissingHazelcastConfiguration extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7167232813058793072L;

    public MissingHazelcastConfiguration() {
        super("Missing Hazelcast configuration [%s.hazelcast]".formatted(LockConfiguration.CONFIGURATION_PROPERTY_PREFIX));
    }
}
