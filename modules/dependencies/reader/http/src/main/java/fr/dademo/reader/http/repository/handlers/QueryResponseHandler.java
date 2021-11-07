package fr.dademo.reader.http.repository.handlers;

import fr.dademo.reader.http.repository.HttpDataQuerierRepository;
import okhttp3.Response;

import java.io.InputStream;

@FunctionalInterface
public interface QueryResponseHandler {

    InputStream handleResponse(Response response, HttpDataQuerierRepository httpDataQuerierRepository);
}
