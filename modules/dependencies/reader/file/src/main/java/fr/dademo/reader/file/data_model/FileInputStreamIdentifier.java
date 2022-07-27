/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.file.data_model;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.tools.HashTools;
import lombok.*;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.charset.StandardCharsets;

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

    private static final long serialVersionUID = 6946849889586524778L;

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

    @Nonnull
    @Override
    public String getUniqueIdentifier() {
        return HashTools.computeHashString(
            HashTools.getHashComputerForAlgorithm("SHA256"),
            file.getAbsolutePath().getBytes(StandardCharsets.UTF_8)
        );
    }
}
