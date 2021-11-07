package fr.dademo.bi.companies.repositories.file.identifier;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

public interface ExpirationConfiguredFileIdentifier extends FileIdentifier<ExpirationConfiguredFileIdentifier> {

    @Nullable
    LocalDateTime getExpirationLocalDateTime();
}
