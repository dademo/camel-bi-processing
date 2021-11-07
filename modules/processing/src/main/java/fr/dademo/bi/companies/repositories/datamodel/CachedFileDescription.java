package fr.dademo.bi.companies.repositories.datamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorDeserializer;
import fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorSerializer;
import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"LOGGER", "valid"})
public class CachedFileDescription implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedFileDescription.class);

    private static final long serialVersionUID = 1302644694795877776L;

    @Nonnull
    private String inputFileIdentifier;

    @Nonnull
    private String finalFileName;

    @Nullable
    @JsonSerialize(contentUsing = CachedFileValidatorSerializer.class)
    @JsonDeserialize(contentUsing = CachedFileValidatorDeserializer.class)
    private List<? extends CachedFileValidator<?>> validators = Collections.emptyList();

    public static CachedFileDescription of(@Nonnull String inputFileIdentifier,
                                           @Nonnull String finalFileName) {
        return of(inputFileIdentifier, finalFileName, Collections.emptyList());
    }

    public static CachedFileDescription of(@Nonnull String inputFileIdentifier,
                                           @Nonnull String finalFileName,
                                           @Nullable Duration expiration) {

        //final var validators = Optional.ofNullable(expiration)
        //        .map(exp -> System.currentTimeMillis() + exp.toMillis())
        //        .map(ExpirationCachedFileValidator::of)
        //        .map(Collections::singletonList)
        //        .orElse(Collections.emptyList());
        return of(inputFileIdentifier, finalFileName, Collections.emptyList());
    }

    public static CachedFileDescription of(@Nonnull String inputFileIdentifier,
                                           @Nonnull String finalFileName,
                                           @Nullable List<? extends CachedFileValidator<?>> validators) {

        final var cachedFileDescription = new CachedFileDescription();

        cachedFileDescription.setInputFileIdentifier(inputFileIdentifier);
        cachedFileDescription.setFinalFileName(finalFileName);
        cachedFileDescription.setValidators(validators);

        return cachedFileDescription;
    }

    public boolean isValid() {

        return Optional.ofNullable(validators)
                .stream()
                .flatMap(Collection::stream)
                .allMatch(this::validate);
    }

    private boolean validate(CachedFileValidator<?> validator) {

        LOGGER.debug("Testing flow `{}` validity against `{}` validator",
                getInputFileIdentifier(),
                validator.getClass().getCanonicalName());

        //final var result = validator.isValid(this);
        final var result = true;
        LOGGER.debug("Got result `{}` when testing flow `{}` validity against `{}` validator",
                result,
                getInputFileIdentifier(),
                validator.getClass().getCanonicalName());
        return result;
    }
}
