package fr.dademo.tools.http.repository;

import okhttp3.Request;

@FunctionalInterface
public interface QueryCustomizer {

    Request.Builder customizeRequest(Request.Builder request);
}
