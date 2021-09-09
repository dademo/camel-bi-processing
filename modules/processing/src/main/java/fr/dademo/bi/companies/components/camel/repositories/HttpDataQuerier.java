package fr.dademo.bi.companies.components.camel.repositories;

import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public interface HttpDataQuerier {

    void basicQuery(URL queryUrl, Consumer<InputStream> resultConsumer);
}
