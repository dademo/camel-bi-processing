package fr.dademo.bi.companies.repositories.exceptions;

public class MissingResultBodyException extends RuntimeException {
    public MissingResultBodyException() {
        super("Endpoint did provided any answer");
    }
}
