package fr.dademo.reader.http.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.validators.exception.InputStreamMimeValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MimeValidator<T extends InputStreamIdentifier<?>> implements InputStreamIdentifierValidator<T> {

    @Nonnull
    private final TikaConfig tikaConfig;

    @Nonnull
    private final String expectedMediaType;

    public static <T extends InputStreamIdentifier<?>> MimeValidator<T> of(
            @Nonnull TikaConfig tikaConfig,
            @Nonnull MediaType expectedMediaType) {
        return of(tikaConfig, expectedMediaType.toString());
    }

    public static <T extends InputStreamIdentifier<?>> MimeValidator<T> of(
            @Nonnull TikaConfig tikaConfig,
            @Nonnull String expectedMediaType) {
        return new MimeValidator<>(tikaConfig, expectedMediaType);
    }

    @Override
    public void validate(T inputStreamIdentifier, InputStream inputStream) throws IOException {

        var computedMediaType = tikaConfig.getDetector()
                .detect(TikaInputStream.get(inputStream), new Metadata())
                .toString();

        if (!computedMediaType.equals(expectedMediaType)) {
            throw new InputStreamMimeValidationException(inputStreamIdentifier, expectedMediaType, computedMediaType);
        }
    }
}
