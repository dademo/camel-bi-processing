package fr.dademo.bi.companies.services.exception;

public final class MissingLocationHeaderException extends RuntimeException {

    private static final long serialVersionUID = -3914345614674956080L;

    public MissingLocationHeaderException() {
        super("The `Location` header is missing fro mthe answer body");
    }
}
