package fr.dademo.bi.companies.repositories.datamodel.serializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CachedFileValidatorFields {

    public static final String FIELD_VALIDATOR_CLASS_NAME = "validatorClass";
    public static final String FIELD_VALIDATOR_VALUE = "value";
}
