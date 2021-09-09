package fr.dademo.bi.companies.components.camel.repositories.exceptions;

import okhttp3.Request;

public class HttpQueryException extends RuntimeException {

    public HttpQueryException(Request request, Exception e) {
        super(String.format("Unable to query on url %s", request.url()), e);
    }
}
