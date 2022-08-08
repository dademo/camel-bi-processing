/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.fast_csv;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedRowResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class FastCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final CsvReader delegate;

    private final boolean closeDelegate;

    private final Iterator<WrappedRowResource> iterator;


    @Nullable
    private final Map<String, Integer> columnsIndexMapping;

    public FastCsvResourcesReaderWrapper(@Nonnull CsvReader delegate, boolean hasHeader, boolean closeDelegate) {

        this.delegate = delegate;
        this.closeDelegate = closeDelegate;

        final var spliterator = delegate.spliterator();
        if (hasHeader) {
            // Opening the stream to read the header
            AtomicReference<Map<String, Integer>> workMapping = new AtomicReference<>(null);
            spliterator.tryAdvance(row -> workMapping.set(mapToColumnsIndexMapping(row)));
            columnsIndexMapping = workMapping.get();
        } else {
            columnsIndexMapping = null;
        }

        iterator = StreamSupport.stream(spliterator, false)
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
    public Iterator<WrappedRowResource> iterator() {
        return iterator;
    }

    private WrappedRowResource toWrappedResource(CsvRow csvRow) {
        return new FastCsvWrappedRowResource(csvRow, columnsIndexMapping);
    }

    private Map<String, Integer> mapToColumnsIndexMapping(CsvRow row) {

        return IntStream.rangeClosed(1, row.getFieldCount())
            .boxed()
            .collect(Collectors.toMap(
                columnIndex -> row.getField(columnIndex - 1),
                Integer::intValue
            ));
    }
}
