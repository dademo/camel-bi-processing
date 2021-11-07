package fr.dademo.bi.companies.repositories.exception;

public class HashSourceDefinitionException extends RuntimeException {

    private static final long serialVersionUID = -1645251556242612196L;

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
