package fr.dademo.reader.http.validators.exception;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.exception.InputStreamIdentifierValidationException;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public class InputStreamHashValidationException extends InputStreamIdentifierValidationException {

    private static final long serialVersionUID = -1046499253110187995L;

    @Nonnull
    private final InputStreamIdentifier<?> inputStreamIdentifier;

    @Nonnull
    private final String expectedHash;

    @Nonnull
    private final String computedHash;

    public InputStreamHashValidationException(@Nonnull InputStreamIdentifier<?> inputStreamIdentifier,
                                              @Nonnull String expectedHash,
                                              @Nonnull String computedHash) {

        super(String.format("Error handing flow `%s`. Expected hash `%s`, got `%s`",
                inputStreamIdentifier.getVerboseDescription(),
                expectedHash,
                computedHash
        ));
        this.inputStreamIdentifier = inputStreamIdentifier;
        this.expectedHash = expectedHash;
        this.computedHash = computedHash;
    }

    public InputStreamHashValidationException(@Nonnull InputStreamIdentifier<?> inputStreamIdentifier,
                                              @Nonnull String expectedHash,
                                              @Nonnull String computedHash,
                                              @Nonnull Throwable throwable) {

        super(String.format("Error handing flow `%s`. Expected hash `%s`, got `%s`",
                        inputStreamIdentifier.getVerboseDescription(),
                        expectedHash,
                        computedHash
                ),
                throwable
        );
        this.inputStreamIdentifier = inputStreamIdentifier;
        this.expectedHash = expectedHash;
        this.computedHash = computedHash;
    }

}
