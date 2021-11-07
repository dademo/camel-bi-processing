package fr.dademo.tools.stream_definitions;

import javax.annotation.Nonnull;

public interface InputStreamIdentifier<T> {

    /**
     * Returning the flow identified by this class.
     * <p>
     * Type may depend on the implementation.
     *
     * @return the stream identified by this class
     */
    @Nonnull
    T getSource();

    @Nonnull
    default String getDescription() {
        return toString();
    }

    @Nonnull
    default String getVerboseDescription() {
        return getDescription();
    }
}
