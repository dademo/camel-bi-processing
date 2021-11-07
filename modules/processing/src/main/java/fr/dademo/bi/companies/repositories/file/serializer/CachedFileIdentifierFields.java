package fr.dademo.bi.companies.repositories.file.serializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CachedFileIdentifierFields {

    public static final String FIELD_IDENTIFIER_CLASS_NAME = "type";
    public static final String FIELD_IDENTIFIER_VALUE = "value";
}
