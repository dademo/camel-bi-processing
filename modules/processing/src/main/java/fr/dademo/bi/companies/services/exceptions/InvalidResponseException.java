package fr.dademo.bi.companies.services.exceptions;

import lombok.Getter;

@Getter
public final class InvalidResponseException extends RuntimeException {

    private final int code;

    public InvalidResponseException(final int code) {
        super(String.format("Invalid service response received (got %d)", code));
        this.code = code;
    }
}
