/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.generic.stream_definitions.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
public interface DataStreamGetter<T extends InputStreamIdentifier<?>> {

    InputStream getInputStream(@Nonnull T inputStreamIdentifier,
                               @Nonnull List<? extends InputStreamIdentifierValidator<T>> streamValidators) throws IOException, InterruptedException;

    default InputStream getInputStream(@Nonnull T inputStreamIdentifier) throws IOException, InterruptedException {
        return getInputStream(inputStreamIdentifier, Collections.emptyList());
    }
}
