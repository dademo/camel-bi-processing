package fr.dademo.bi.companies.repositories.exception;

import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;

public class HashMismatchException extends RuntimeException {

    private static final long serialVersionUID = -8322269111030717937L;

    public HashMismatchException(FileIdentifier<?> inputFileIdentifier, String expected, String fileHash) {
        super(String.format("Hash for stream `%s` does not match expected (expected `%s`, got `%s`)",
                inputFileIdentifier.getBaseUrl(),
                expected,
                fileHash
        ));
    }
}
