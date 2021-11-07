package fr.dademo.tools.http.repository.exception;

import okhttp3.Response;

public class FailedQueryException extends RuntimeException {

    private static final long serialVersionUID = 7406651192145018752L;

    public FailedQueryException(Response response) {
        super(String.format("Query on url [%s] failed (%s)",
                response.request().url(),
                response
        ));
    }
}
