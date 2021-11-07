package fr.dademo.bi.companies.repositories.file.validators;

import fr.dademo.bi.companies.repositories.file.identifier.ExpirationConfiguredFileIdentifier;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Optional;

public class ExpirationCachedFileValidator implements CachedFileValidator<ExpirationConfiguredFileIdentifier> {

    @Override
    public boolean isValid(@Nonnull ExpirationConfiguredFileIdentifier fileIdentifier) {

        return Optional.ofNullable(fileIdentifier.getExpirationLocalDateTime())
                .map(sourceExpiration -> sourceExpiration.isAfter(LocalDateTime.now()))
                .orElse(true);
    }
}
