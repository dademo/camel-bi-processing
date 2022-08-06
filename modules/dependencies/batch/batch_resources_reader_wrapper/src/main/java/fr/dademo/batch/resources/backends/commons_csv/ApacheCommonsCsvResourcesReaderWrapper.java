/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.commons_csv;

import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.WrappedResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.Nonnull;
import java.util.Iterator;

public class ApacheCommonsCsvResourcesReaderWrapper implements ResourcesReaderWrapper {

    @Nonnull
    private final CSVParser delegate;

    private final boolean closeDelegate;


    public ApacheCommonsCsvResourcesReaderWrapper(@Nonnull CSVParser delegate, boolean closeDelegate) {

        this.delegate = delegate;
        this.closeDelegate = closeDelegate;
    }

    @Override
    public Iterator<WrappedResource> iterator() {

        return delegate.stream()
            .map(this::toWrappedResource)
            .iterator();
    }

    @Override
    public void close() throws Exception {

        if (closeDelegate) {
            delegate.close();
        }
    }

    private WrappedResource toWrappedResource(CSVRecord csvRecord) {
        return new ApacheCommonsCsvWrappedResource(csvRecord);
    }
}
