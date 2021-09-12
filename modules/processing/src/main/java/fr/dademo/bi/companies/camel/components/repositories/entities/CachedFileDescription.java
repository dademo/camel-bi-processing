package fr.dademo.bi.companies.camel.components.repositories.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;

@Data
@JsonIgnoreProperties({"valid"})
public class CachedFileDescription {

    private String inputFileIdentifier;
    private String finalFileName;
    private long validUntil;

    public static CachedFileDescription of(@Nonnull String inputFileIdentifier,
                                           @Nonnull String finalFileName,
                                           @Nullable Duration expiration) {

        var cachedFileDescription = new CachedFileDescription();

        cachedFileDescription.setInputFileIdentifier(inputFileIdentifier);
        cachedFileDescription.setFinalFileName(finalFileName);
        cachedFileDescription.setValidUntil(Optional.ofNullable(expiration)
                .map(exp -> System.currentTimeMillis() + exp.toMillis())
                .orElse(0L));

        return cachedFileDescription;
    }

    public boolean isValid() {
        return validUntil <= 0 ||
                System.currentTimeMillis() > validUntil;
    }
}
