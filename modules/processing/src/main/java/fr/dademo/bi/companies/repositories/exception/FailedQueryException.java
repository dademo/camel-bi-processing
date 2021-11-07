package fr.dademo.bi.companies.repositories.exception;

import okhttp3.Response;

public class FailedQueryException extends RuntimeException {

    private static final long serialVersionUID = -6794834207178762523L;

    public FailedQueryException(Response response) {
        super(String.format("Query on url [%s] failed (%s)",
                response.request().url(),
                response
        ));
    }
}
