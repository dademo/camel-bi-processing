/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.named_fast_csv;

import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRecord;
import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedRowResource;

import jakarta.annotation.Nonnull;

import java.util.Iterator;

public class NamedFastCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final CsvReader<NamedCsvRecord> delegate;

    private final boolean closeDelegate;


    public NamedFastCsvResourcesReaderWrapper(@Nonnull CsvReader<NamedCsvRecord> delegate, boolean closeDelegate) {

        this.delegate = delegate;
        this.closeDelegate = closeDelegate;
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
        return delegate.stream()
            .map(this::toWrappedResource)
            .iterator();
    }

    private WrappedRowResource toWrappedResource(NamedCsvRecord namedCsvRecord) {
        return new NamedFastCsvWrappedRecordResource(namedCsvRecord);
    }
}
