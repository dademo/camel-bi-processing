package fr.dademo.bi.companies.repositories.datamodel.serializer.exception;

public abstract class ValidatorFieldNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1155133661046756745L;

    protected ValidatorFieldNotFoundException(String fieldName) {
        super(String.format("Field `%s` not found in serialized value", fieldName));
    }
}
