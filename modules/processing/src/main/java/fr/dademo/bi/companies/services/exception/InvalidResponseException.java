package fr.dademo.bi.companies.services.exception;

import lombok.Getter;

@Getter
public final class InvalidResponseException extends RuntimeException {

    private static final long serialVersionUID = -4766170870767677543L;

    private final int code;

    public InvalidResponseException(final int code) {
        super(String.format("Invalid service response received (got %d)", code));
        this.code = code;
    }
}
