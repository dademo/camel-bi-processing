package fr.dademo.tools.cache.repository.exception;

public class NotADirectoryException extends RuntimeException {

    private static final long serialVersionUID = -1347099597070844462L;

    public NotADirectoryException(String path) {
        super(String.format("Path %s is not a directory", path));
    }
}
