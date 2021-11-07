package fr.dademo.tools.http.repository.exception;

public class MissingResultBodyException extends RuntimeException {

    private static final long serialVersionUID = -3412548216284615256L;

    public MissingResultBodyException() {
        super("Endpoint did provided any answer");
    }
}
