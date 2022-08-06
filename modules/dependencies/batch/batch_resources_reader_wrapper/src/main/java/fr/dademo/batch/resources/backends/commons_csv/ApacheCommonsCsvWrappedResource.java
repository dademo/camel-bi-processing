/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.commons_csv;

import fr.dademo.batch.resources.BaseWrappedResource;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ApacheCommonsCsvWrappedResource extends BaseWrappedResource {

    @Nonnull
    private final CSVRecord delegate;

    public ApacheCommonsCsvWrappedResource(@Nonnull CSVRecord delegate) {

        this.delegate = delegate;
        setColumnsIndexMapping(delegate.getParser().getHeaderMap());
    }

    @Nullable
    @Override
    public String getString(int columnIndex) {
        return delegate.get(columnIndex - 1);
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {
        return delegate.get(columnLabel);
    }
}
