package fr.dademo.bi.companies.repositories.exception;

public class MissingResultBodyException extends RuntimeException {

    private static final long serialVersionUID = -8467612176115441152L;

    public MissingResultBodyException() {
        super("Endpoint did provided any answer");
    }
}
