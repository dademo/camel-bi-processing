package fr.dademo.bi.companies.repositories.exceptions;

public class UnexpectedRedirectResponseException extends RuntimeException {

    public UnexpectedRedirectResponseException() {
        super("Unexpected redirection answer");
    }
}
