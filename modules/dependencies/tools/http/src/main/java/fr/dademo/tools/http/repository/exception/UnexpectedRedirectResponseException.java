package fr.dademo.tools.http.repository.exception;

public class UnexpectedRedirectResponseException extends RuntimeException {

    private static final long serialVersionUID = 4537658660276565516L;

    public UnexpectedRedirectResponseException() {
        super("Unexpected redirection answer");
    }
}
