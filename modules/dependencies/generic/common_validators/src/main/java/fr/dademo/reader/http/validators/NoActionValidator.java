/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
public class NoActionValidator<T extends InputStreamIdentifier<?>> implements InputStreamIdentifierValidator<T> {

    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    public void validate(T inputStreamIdentifier, InputStream inputStream) throws IOException {

        while (inputStream.read() != -1) {
            // Nothing to do, we just read the stream
        }
    }
}
