package fr.dademo.bi.companies.camel.components.repositories.exceptions;

public class MissingResultBodyException extends RuntimeException {
    public MissingResultBodyException() {
        super("Endpoint did provided any answer");
    }
}
