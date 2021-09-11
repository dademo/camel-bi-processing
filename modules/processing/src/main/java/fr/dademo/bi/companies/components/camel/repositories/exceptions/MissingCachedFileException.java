package fr.dademo.bi.companies.components.camel.repositories.exceptions;

public class MissingCachedFileException extends RuntimeException {

    public MissingCachedFileException(String streamIdentifier) {
        super(String.format("Unable to find cached %s stream", streamIdentifier));
    }
}
