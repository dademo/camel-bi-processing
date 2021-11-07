package fr.dademo.tools.http.repository.handlers;

import fr.dademo.tools.http.repository.HttpDataQuerier;
import fr.dademo.tools.http.repository.exception.FailedQueryException;
import fr.dademo.tools.http.repository.exception.MissingResultBodyException;
import fr.dademo.tools.http.repository.exception.UnexpectedRedirectResponseException;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.InputStream;
import java.util.Optional;

public class DefaultQueryResponseHandler implements QueryResponseHandler {

    @Override
    public InputStream handleResponse(Response response, HttpDataQuerier httpDataQuerier) {

        if (!response.isSuccessful()) {
            throw new FailedQueryException(response);
        }
        if (response.isRedirect()) {
            throw new UnexpectedRedirectResponseException();
        }


        return Optional.ofNullable(response.body())
                .map(ResponseBody::byteStream)
                .orElseThrow(MissingResultBodyException::new);
    }
}
