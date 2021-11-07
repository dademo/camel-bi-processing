package fr.dademo.bi.companies.repositories.datamodel.serializer.exception;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_VALUE;

public final class ValidatorClassFieldNotFoundException extends ValidatorFieldNotFoundException {

    private static final long serialVersionUID = 6172295473224844037L;

    public ValidatorClassFieldNotFoundException() {
        super(FIELD_VALIDATOR_VALUE);
    }
}
