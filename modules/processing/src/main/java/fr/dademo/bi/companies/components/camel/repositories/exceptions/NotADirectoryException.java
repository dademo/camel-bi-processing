package fr.dademo.bi.companies.components.camel.repositories.exceptions;

public class NotADirectoryException extends RuntimeException {
    public NotADirectoryException(String fileName) {
        super(String.format("File %s is not a directory", fileName));
    }
}
