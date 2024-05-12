/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.http.validators.exception.InputStreamHashValidationException;
import fr.dademo.tools.tools.HashTools;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
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
