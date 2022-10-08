/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;

/**
 * @author dademo
 */
public interface CacheFlowIgnoreChecker {

    /**
     * Checks if a flow is allowed to be cached for the given {@link InputStreamIdentifier}.
     *
     * @param inputStreamIdentifier the stream identifier to check.
     * @return if the flow is allowed to be cached.
     */
    boolean isFlowAllowedToBeCached(InputStreamIdentifier<?> inputStreamIdentifier);
}
