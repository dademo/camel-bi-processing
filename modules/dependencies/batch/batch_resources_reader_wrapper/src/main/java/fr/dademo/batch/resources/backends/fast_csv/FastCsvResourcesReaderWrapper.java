/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.fast_csv;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FastCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final CsvReader delegate;

    private final boolean closeDelegate;

    private final Iterator<WrappedResource> iterator;


    @Nullable
    private final Map<String, Integer> columnsIndexMapping;

    public FastCsvResourcesReaderWrapper(@Nonnull CsvReader delegate, boolean hasHeader, boolean closeDelegate) {

        this.delegate = delegate;
        this.closeDelegate = closeDelegate;

        final var delegateStream = delegate.stream();
        if (hasHeader) {
            // Opening the stream to read the header
            columnsIndexMapping = delegateStream.findFirst()
                .map(this::mapTocolumnsIndexMapping)
                .orElse(null);
        } else {
            columnsIndexMapping = null;
        }

        iterator = delegateStream
            .map(this::toWrappedResource)
            .iterator();
    }

    @Override
    public void close() throws Exception {

        if (closeDelegate) {
            delegate.close();
        }
    }

    @Override
    public Iterator<WrappedResource> iterator() {
        return iterator;
    }

    private WrappedResource toWrappedResource(CsvRow csvRow) {
        return new FastCsvWrappedResource(csvRow, columnsIndexMapping);
    }

    private Map<String, Integer> mapTocolumnsIndexMapping(CsvRow row) {

        return IntStream.rangeClosed(1, row.getFieldCount())
            .boxed()
            .collect(Collectors.toMap(
                row::getField,
                Integer::intValue
            ));
    }
}
