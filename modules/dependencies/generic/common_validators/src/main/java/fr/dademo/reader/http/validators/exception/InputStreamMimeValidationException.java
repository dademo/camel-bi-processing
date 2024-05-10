/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.validators.exception;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.exception.InputStreamIdentifierValidationException;
import jakarta.annotation.Nonnull;
import lombok.Getter;

/**
 * @author dademo
 */
@Getter
public class InputStreamMimeValidationException extends InputStreamIdentifierValidationException {

    private static final long serialVersionUID = -1046499253110187995L;

    @Nonnull
    private final transient InputStreamIdentifier<?> inputStreamIdentifier;

    @Nonnull
    private final String expectedMime;

    @Nonnull
    private final String computedMime;

    public InputStreamMimeValidationException(@Nonnull InputStreamIdentifier<?> inputStreamIdentifier,
                                              @Nonnull String expectedMime,
                                              @Nonnull String computedMime) {

        super(String.format("Error handing flow `%s`. Expected mime `%s`, got `%s`",
            inputStreamIdentifier.getVerboseDescription(),
            expectedMime,
            computedMime
        ));
        this.inputStreamIdentifier = inputStreamIdentifier;
        this.expectedMime = expectedMime;
        this.computedMime = computedMime;
    }

    public InputStreamMimeValidationException(@Nonnull InputStreamIdentifier<?> inputStreamIdentifier,
                                              @Nonnull String expectedMime,
                                              @Nonnull String computedMime,
                                              @Nonnull Throwable throwable) {

        super(String.format("Error handing flow `%s`. Expected hash `%s`, got `%s`",
                inputStreamIdentifier.getVerboseDescription(),
                expectedMime,
                computedMime
            ),
            throwable
        );
        this.inputStreamIdentifier = inputStreamIdentifier;
        this.expectedMime = expectedMime;
        this.computedMime = computedMime;
    }

}
