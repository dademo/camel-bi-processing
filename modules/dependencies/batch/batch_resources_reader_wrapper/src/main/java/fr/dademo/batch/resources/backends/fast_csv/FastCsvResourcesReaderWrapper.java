/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.fast_csv;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRecord;
import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedRowResource;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@SuppressWarnings("unused")
public class FastCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final CsvReader<CsvRecord> delegate;

    private final boolean closeDelegate;

    private final Iterator<WrappedRowResource> iterator;


    @Nullable
    private final Map<String, Integer> columnsIndexMapping;

    public FastCsvResourcesReaderWrapper(@Nonnull CsvReader<CsvRecord> delegate, boolean hasHeader, boolean closeDelegate) {

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

    @Nonnull
    @Override
    public Iterator<WrappedRowResource> iterator() {
        return iterator;
    }

    private WrappedRowResource toWrappedResource(CsvRecord csvRecord) {
        return new FastCsvWrappedRecordResource(csvRecord, columnsIndexMapping);
    }

    private Map<String, Integer> mapToColumnsIndexMapping(CsvRecord row) {

        return IntStream.rangeClosed(1, row.getFieldCount())
            .boxed()
            .collect(Collectors.toMap(
                columnIndex -> row.getField(columnIndex - 1),
                Integer::intValue
            ));
    }
}
