package fr.dademo.tools.http.validators;

import fr.dademo.tools.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.stream_definitions.InputStreamIdentifierValidator;

import java.io.IOException;
import java.io.InputStream;

public class NoActionValidator<T extends InputStreamIdentifier<?>> implements InputStreamIdentifierValidator<T> {

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public void validate(T inputStreamIdentifier, InputStream inputStream) throws IOException {

        while (inputStream.read() != -1) {
            // Nothing to do, we just read the stream
        }
    }
}
