package fr.dademo.bi.companies.services.exceptions;

public final class TooManyRedirectException extends RuntimeException {

    public TooManyRedirectException() {
        super("Too many redirect");
    }
}
