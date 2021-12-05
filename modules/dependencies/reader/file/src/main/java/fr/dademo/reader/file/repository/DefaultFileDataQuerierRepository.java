/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.file.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifierValidator;
import fr.dademo.reader.file.data_model.FileInputStreamIdentifier;
import fr.dademo.reader.file.repository.context.FileValidationContext;

import javax.annotation.Nonnull;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author dademo
 */
public class DefaultFileDataQuerierRepository implements FileDataQuerierRepository {

    @Override
    public InputStream getInputStream(@Nonnull FileInputStreamIdentifier inputStreamIdentifier,
                                      @Nonnull List<? extends InputStreamIdentifierValidator<FileInputStreamIdentifier>> streamValidators) throws IOException {
        return new FileValidationContext<>(
            new FileInputStream(inputStreamIdentifier.getSource()),
            inputStreamIdentifier,
            streamValidators
        );
    }
}
