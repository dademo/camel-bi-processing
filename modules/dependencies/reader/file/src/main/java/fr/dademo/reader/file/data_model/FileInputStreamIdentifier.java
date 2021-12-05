/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.file.data_model;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import lombok.*;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * @author dademo
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class FileInputStreamIdentifier implements InputStreamIdentifier<File> {

    @Nonnull
    private File file;

    @Nonnull
    @Override
    public File getSource() {
        return getFile();
    }

    public void setSource(@Nonnull File file) {
        this.file = file;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return String.format("File input stream of file `%s`", getFile());
    }
}
