package fr.dademo.bi.companies.repositories.exception;

public class NotADirectoryException extends RuntimeException {

    private static final long serialVersionUID = -7074052944251208608L;

    public NotADirectoryException(String fileName) {
        super(String.format("File %s is not a directory", fileName));
    }
}
