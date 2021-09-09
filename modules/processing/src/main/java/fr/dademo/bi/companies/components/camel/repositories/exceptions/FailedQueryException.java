package fr.dademo.bi.companies.components.camel.repositories.exceptions;

import okhttp3.Response;

public class FailedQueryException extends RuntimeException {

    public FailedQueryException(Response response) {
        super(String.format("Query on url [%s] failed (%s)",
                response.request().url(),
                response
        ));
    }
}
