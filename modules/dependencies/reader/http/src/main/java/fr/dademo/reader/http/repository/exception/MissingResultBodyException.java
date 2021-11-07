package fr.dademo.reader.http.repository.exception;

import okhttp3.Response;

public class MissingResultBodyException extends BaseHttpQueryException {

    private static final long serialVersionUID = -3412548216284615256L;

    public MissingResultBodyException(Response queryResponse) {
        super("Endpoint did provided any answer", queryResponse);
    }
}
