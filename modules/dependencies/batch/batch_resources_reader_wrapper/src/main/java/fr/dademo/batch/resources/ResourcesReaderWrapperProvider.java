/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.NamedCsvReader;
import fr.dademo.batch.resources.backends.apache_commons_csv.ApacheCommonsCsvResourcesReaderWrapper;
import fr.dademo.batch.resources.backends.fast_csv.FastCsvResourcesReaderWrapper;
import fr.dademo.batch.resources.backends.named_fast_csv.NamedFastCsvResourcesReaderWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVParser;

import javax.annotation.Nonnull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourcesReaderWrapperProvider {

    public static final boolean DEFAULT_CLOSE_DELEGATE = true;

    public static ResourcesReaderWrapper of(@Nonnull CSVParser delegate) {
        return new ApacheCommonsCsvResourcesReaderWrapper(delegate, DEFAULT_CLOSE_DELEGATE);
    }

    public static ResourcesReaderWrapper of(@Nonnull CSVParser delegate, boolean closeDelegate) {
        return new ApacheCommonsCsvResourcesReaderWrapper(delegate, closeDelegate);
    }

    public static ResourcesReaderWrapper of(@Nonnull CsvReader delegate, boolean hasHeader) {
        return new FastCsvResourcesReaderWrapper(delegate, hasHeader, DEFAULT_CLOSE_DELEGATE);
    }

    public static ResourcesReaderWrapper of(@Nonnull CsvReader delegate, boolean hasHeader, boolean closeDelegate) {
        return new FastCsvResourcesReaderWrapper(delegate, hasHeader, closeDelegate);
    }

    public static ResourcesReaderWrapper of(@Nonnull NamedCsvReader delegate) {
        return new NamedFastCsvResourcesReaderWrapper(delegate, DEFAULT_CLOSE_DELEGATE);
    }

    public static ResourcesReaderWrapper of(@Nonnull NamedCsvReader delegate, boolean closeDelegate) {
        return new NamedFastCsvResourcesReaderWrapper(delegate, closeDelegate);
    }
}
