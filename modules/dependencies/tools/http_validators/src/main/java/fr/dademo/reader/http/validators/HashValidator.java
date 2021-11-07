package fr.dademo.reader.http.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.validators.exception.InputStreamHashValidationException;
import fr.dademo.tools.tools.HashTools;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HashValidator<T extends InputStreamIdentifier<?>> implements InputStreamIdentifierValidator<T> {

    @Nonnull
    private final MessageDigest messageDigest;

    @Nonnull
    private final String expectedHexHash;

    public static <T extends InputStreamIdentifier<?>> HashValidator<T> of(@Nonnull String hashAlgorithm,
                                                                           @Nonnull String expectedHexHash) {
        return of(HashTools.getHashComputerForAlgorithm(hashAlgorithm), expectedHexHash);
    }

    public static <T extends InputStreamIdentifier<?>> HashValidator<T> of(@Nonnull MessageDigest messageDigest,
                                                                           @Nonnull String expectedHexHash) {
        return new HashValidator<>(messageDigest, expectedHexHash.toUpperCase());
    }

    @Override
    public void validate(T inputStreamIdentifier, InputStream inputStream) throws IOException {

        final var computedHash = HashTools.computeHashString(messageDigest, inputStream);
        if (!expectedHexHash.equals(computedHash)) {
            throw new InputStreamHashValidationException(inputStreamIdentifier, expectedHexHash, computedHash);
        }

    }
}
