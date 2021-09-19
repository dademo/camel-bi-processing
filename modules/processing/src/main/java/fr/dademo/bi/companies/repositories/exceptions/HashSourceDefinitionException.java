package fr.dademo.bi.companies.repositories.exceptions;

public class HashSourceDefinitionException extends RuntimeException {

    public HashSourceDefinitionException(String definition) {
        super(formatMessage(definition));
    }

    public HashSourceDefinitionException(String definition, Throwable cause) {
        super(formatMessage(definition), cause);
    }

    private static String formatMessage(String definition) {
        return String.format("Hash definition has invalid format (%s)", definition);
    }
}
