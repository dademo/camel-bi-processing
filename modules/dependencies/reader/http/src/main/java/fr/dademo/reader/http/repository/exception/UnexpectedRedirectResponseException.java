package fr.dademo.reader.http.repository.exception;

import okhttp3.Response;

public class UnexpectedRedirectResponseException extends BaseHttpQueryException {

    private static final long serialVersionUID = 4537658660276565516L;

    public UnexpectedRedirectResponseException(Response queryResponse) {
        super("Unexpected redirection answer", queryResponse);
    }
}
