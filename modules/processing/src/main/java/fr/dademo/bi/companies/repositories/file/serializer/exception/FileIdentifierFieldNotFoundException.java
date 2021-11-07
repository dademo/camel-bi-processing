package fr.dademo.bi.companies.repositories.file.serializer.exception;

public abstract class FileIdentifierFieldNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1155133661046756745L;

    protected FileIdentifierFieldNotFoundException(String fieldName) {
        super(String.format("Field `%s` not found in serialized value", fieldName));
    }
}
