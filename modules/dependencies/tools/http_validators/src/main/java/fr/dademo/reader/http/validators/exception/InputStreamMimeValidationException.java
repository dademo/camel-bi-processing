package fr.dademo.reader.http.validators.exception;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.exception.InputStreamIdentifierValidationException;
import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public class InputStreamMimeValidationException extends InputStreamIdentifierValidationException {

    private static final long serialVersionUID = -1046499253110187995L;

    @Nonnull
    private final InputStreamIdentifier<?> inputStreamIdentifier;

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
