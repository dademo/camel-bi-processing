package fr.dademo.bi.companies.camel.components.repositories.exceptions;

public class HashMismatchException extends RuntimeException {

    public HashMismatchException(String inputFileIdentifier, String expected, String fileHash) {
        super(String.format("Hash for stream %s does not match expected (expected %s, got %s)",
                inputFileIdentifier,
                expected,
                fileHash
        ));
    }
}
