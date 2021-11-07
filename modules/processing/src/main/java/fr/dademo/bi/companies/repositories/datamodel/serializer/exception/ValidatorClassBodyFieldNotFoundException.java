package fr.dademo.bi.companies.repositories.datamodel.serializer.exception;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_CLASS_NAME;

public final class ValidatorClassBodyFieldNotFoundException extends ValidatorFieldNotFoundException {

    private static final long serialVersionUID = 2506206271729730424L;

    public ValidatorClassBodyFieldNotFoundException() {
        super(FIELD_VALIDATOR_CLASS_NAME);
    }
}
