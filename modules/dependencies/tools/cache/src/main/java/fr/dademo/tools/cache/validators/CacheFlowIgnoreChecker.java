package fr.dademo.tools.cache.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;

public interface CacheFlowIgnoreChecker<T extends InputStreamIdentifier<?>> {

    /**
     * Checks if a flow is allowed to be cached for the given {@link InputStreamIdentifier}.
     *
     * @param inputStreamIdentifier the stream identifier to check.
     * @return if the flow is allowed to be cached.
     */
    boolean isFlowAllowedToBeCached(T inputStreamIdentifier);
}
