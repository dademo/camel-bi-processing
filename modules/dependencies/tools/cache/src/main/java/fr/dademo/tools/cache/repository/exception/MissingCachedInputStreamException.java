package fr.dademo.tools.cache.repository.exception;

import fr.dademo.tools.stream_definitions.InputStreamIdentifier;

public class MissingCachedInputStreamException extends RuntimeException {

    private static final long serialVersionUID = -1347099597070844462L;

    public MissingCachedInputStreamException(InputStreamIdentifier<?> inputStreamIdentifier) {
        super(String.format("Missing cache for input stream `%s`", inputStreamIdentifier.getDescription()));
    }
}
