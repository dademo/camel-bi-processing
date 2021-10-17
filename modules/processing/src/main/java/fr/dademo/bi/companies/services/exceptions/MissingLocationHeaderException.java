package fr.dademo.bi.companies.services.exceptions;

public final class MissingLocationHeaderException extends RuntimeException {

    public MissingLocationHeaderException() {
        super("The `Location` header is missing fro mthe answer body");
    }
}
