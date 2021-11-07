package fr.dademo.data.generic.stream_definitions.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public interface DataStreamGetter<T extends InputStreamIdentifier<?>> {

    InputStream getInputStream(@Nonnull T inputStreamIdentifier,
                               @Nonnull List<? extends InputStreamIdentifierValidator<T>> streamValidators) throws IOException;

    default InputStream getInputStream(@Nonnull T inputStreamIdentifier) throws IOException {
        return getInputStream(inputStreamIdentifier, Collections.emptyList());
    }
}
