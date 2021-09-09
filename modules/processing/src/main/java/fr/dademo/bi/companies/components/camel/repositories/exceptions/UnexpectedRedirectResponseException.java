package fr.dademo.bi.companies.components.camel.repositories.exceptions;

public class UnexpectedRedirectResponseException extends RuntimeException {

    public UnexpectedRedirectResponseException() {
        super("Unexpected redirection answer");
    }
}
