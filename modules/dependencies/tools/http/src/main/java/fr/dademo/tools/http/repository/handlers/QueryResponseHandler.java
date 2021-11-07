package fr.dademo.tools.http.repository.handlers;

import fr.dademo.tools.http.repository.HttpDataQuerier;
import okhttp3.Response;

import java.io.InputStream;

@FunctionalInterface
public interface QueryResponseHandler {

    InputStream handleResponse(Response response, HttpDataQuerier httpDataQuerier);
}
