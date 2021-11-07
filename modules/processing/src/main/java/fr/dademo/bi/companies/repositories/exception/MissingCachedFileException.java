package fr.dademo.bi.companies.repositories.exception;

import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;

public class MissingCachedFileException extends RuntimeException {

    private static final long serialVersionUID = -372547520766434831L;

    public MissingCachedFileException(FileIdentifier<?> fileIdentifier) {
        super(String.format("Unable to find cached %s stream", fileIdentifier.getBaseUrl()));
    }
}
