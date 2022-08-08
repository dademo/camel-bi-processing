/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.named_fast_csv;

import de.siegmar.fastcsv.reader.NamedCsvReader;
import de.siegmar.fastcsv.reader.NamedCsvRow;
import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedRowResource;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class NamedFastCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final NamedCsvReader delegate;

    private final boolean closeDelegate;


    public NamedFastCsvResourcesReaderWrapper(@Nonnull NamedCsvReader delegate, boolean closeDelegate) {

        this.delegate = delegate;
        this.closeDelegate = closeDelegate;
    }

    @Override
    public void close() throws Exception {

        if (closeDelegate) {
            delegate.close();
        }
    }

    @Override
    public Iterator<WrappedRowResource> iterator() {
        return delegate.stream()
            .map(this::toWrappedResource)
            .iterator();
    }

    private WrappedRowResource toWrappedResource(NamedCsvRow namedCsvRow) {
        return new NamedFastCsvWrappedRowResource(namedCsvRow);
    }
}
