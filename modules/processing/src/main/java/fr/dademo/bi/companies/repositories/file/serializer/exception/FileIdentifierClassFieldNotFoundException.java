package fr.dademo.bi.companies.repositories.file.serializer.exception;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_VALUE;

public final class FileIdentifierClassFieldNotFoundException extends FileIdentifierFieldNotFoundException {

    private static final long serialVersionUID = 6172295473224844037L;

    public FileIdentifierClassFieldNotFoundException() {
        super(FIELD_VALIDATOR_VALUE);
    }
}
