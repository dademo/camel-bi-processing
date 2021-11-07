package fr.dademo.reader.http.repository.exception;

import okhttp3.Response;

import javax.annotation.Nonnull;

public class FailedQueryException extends BaseHttpQueryException {

    private static final long serialVersionUID = 7406651192145018752L;

    public FailedQueryException(@Nonnull Response queryResponse) {
        super(String.format("Query on url [%s] failed (%s)",
                queryResponse.request().url(),
                queryResponse
        ), queryResponse);
    }
}
