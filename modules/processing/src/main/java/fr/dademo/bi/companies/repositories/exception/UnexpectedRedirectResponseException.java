package fr.dademo.bi.companies.repositories.exception;

public class UnexpectedRedirectResponseException extends RuntimeException {

    private static final long serialVersionUID = 3282183652860705926L;

    public UnexpectedRedirectResponseException() {
        super("Unexpected redirection answer");
    }
}
