package fr.dademo.bi.companies.camel.components.repositories.exceptions;

public class UnexpectedRedirectResponseException extends RuntimeException {

    public UnexpectedRedirectResponseException() {
        super("Unexpected redirection answer");
    }
}
