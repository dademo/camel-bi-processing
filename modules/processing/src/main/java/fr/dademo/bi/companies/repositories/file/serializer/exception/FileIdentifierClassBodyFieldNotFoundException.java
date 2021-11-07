package fr.dademo.bi.companies.repositories.file.serializer.exception;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_CLASS_NAME;

public final class FileIdentifierClassBodyFieldNotFoundException extends FileIdentifierFieldNotFoundException {

    private static final long serialVersionUID = 2506206271729730424L;

    public FileIdentifierClassBodyFieldNotFoundException() {
        super(FIELD_VALIDATOR_CLASS_NAME);
    }
}
