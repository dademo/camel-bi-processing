package fr.dademo.tools.stream_definitions;

import fr.dademo.tools.stream_definitions.exception.InputStreamIdentifierValidationException;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamIdentifierValidator<I extends InputStreamIdentifier<?>> {

    /**
     * A validator that checks that the given input stream is valid.
     * If not, it must raise an {@link InputStreamIdentifierValidationException} exception.
     * <p>
     * Depending on the implementation, the input stream can be provided during or after
     * the execution, but an {@link InputStreamIdentifierValidationException}
     * <strong>MUST</strong> will invalidate the input stream and avoid additional
     * processing (like caching data).
     *
     * @param inputStreamIdentifier an {@link InputStreamIdentifier} describing the flow to validate
     * @param inputStream           an {@link InputStream} that will be validated
     * @throws InputStreamIdentifierValidationException the input stream is not valid
     *                                                  and further processing <strong>MUST</strong> stop.
     * @throws IOException                              an error occurred while reading the given stream.
     * @see InputStreamIdentifier
     */
    void validate(I inputStreamIdentifier, InputStream inputStream) throws InputStreamIdentifierValidationException, IOException;
}
