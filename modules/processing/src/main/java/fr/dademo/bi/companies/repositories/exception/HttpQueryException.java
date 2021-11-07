package fr.dademo.bi.companies.repositories.exception;

import okhttp3.Request;

public class HttpQueryException extends RuntimeException {

    private static final long serialVersionUID = 6026449476456846321L;

    public HttpQueryException(Request request, Exception e) {
        super(String.format("Unable to query on url %s", request.url()), e);
    }
}
