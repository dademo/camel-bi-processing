package fr.dademo.reader.http.repository.handlers;

import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import fr.dademo.reader.http.repository.exception.FailedQueryException;
import fr.dademo.reader.http.repository.exception.MissingResultBodyException;
import fr.dademo.reader.http.repository.exception.UnexpectedRedirectResponseException;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.InputStream;
import java.util.Optional;

public class DefaultQueryResponseHandler implements QueryResponseHandler {

    @Override
    public InputStream handleResponse(Response response, HttpDataQuerierRepository httpDataQuerierRepository) {

        if (!response.isSuccessful()) {
            throw new FailedQueryException(response);
        }
        if (response.isRedirect()) {
            throw new UnexpectedRedirectResponseException(response);
        }

        return Optional.ofNullable(response.body())
                .map(ResponseBody::byteStream)
                .orElseThrow(() -> new MissingResultBodyException(response));
    }
}
