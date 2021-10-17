package fr.dademo.bi.companies.services.exceptions;

import lombok.Getter;

@Getter
public final class InvalidResponseException extends RuntimeException {

    private final int code;

    public InvalidResponseException(final int code) {
        super("Invalid service response received");
        this.code = code;
    }
}
