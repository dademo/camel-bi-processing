package fr.dademo.bi.companies.repositories.file.identifier;

import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;
import fr.dademo.bi.companies.repositories.file.validators.ExpirationCachedFileValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExpirationConfiguredFileIdentifierImpl extends BaseFileIdentifierImpl<ExpirationConfiguredFileIdentifier> implements ExpirationConfiguredFileIdentifier {

    private static final long serialVersionUID = -2460745130203511417L;
    @Nullable
    private LocalDateTime expirationLocalDateTime;

    public ExpirationConfiguredFileIdentifierImpl(@Nonnull URL baseUrl, @Nullable URL queryUrl,
                                                  @Nullable LocalDateTime expirationLocalDateTime) {

        super(baseUrl, Optional.ofNullable(queryUrl).orElse(baseUrl));
        this.expirationLocalDateTime = expirationLocalDateTime;
    }

    public static ExpirationConfiguredFileIdentifierImpl forDuration(@Nonnull URL baseUrl, Duration duration) {
        return new ExpirationConfiguredFileIdentifierImpl(baseUrl, null, LocalDateTime.now().plus(duration));
    }

    public static ExpirationConfiguredFileIdentifierImpl forDuration(@Nonnull URL baseUrl, @Nullable URL queryUrl,
                                                                     Duration duration) {
        return new ExpirationConfiguredFileIdentifierImpl(baseUrl, queryUrl, LocalDateTime.now().plus(duration));
    }

    @Override
    public List<CachedFileValidator<ExpirationConfiguredFileIdentifier>> getValidators() {
        return Collections.singletonList(new ExpirationCachedFileValidator());
    }
}
