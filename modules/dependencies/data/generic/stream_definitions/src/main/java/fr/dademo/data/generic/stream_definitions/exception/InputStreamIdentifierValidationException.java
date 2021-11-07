package fr.dademo.data.generic.stream_definitions.exception;

import java.io.IOException;

public abstract class InputStreamIdentifierValidationException extends IOException {

    private static final long serialVersionUID = -3976056791520761440L;

    protected InputStreamIdentifierValidationException() {
    }

    protected InputStreamIdentifierValidationException(String message) {
        super(message);
    }

    protected InputStreamIdentifierValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected InputStreamIdentifierValidationException(Throwable cause) {
        super(cause);
    }
}
