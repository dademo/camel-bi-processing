package fr.dademo.bi.companies.services.exception;

public final class TooManyRedirectException extends RuntimeException {

    private static final long serialVersionUID = 458144734383201811L;

    public TooManyRedirectException() {
        super("Too many redirect");
    }
}
