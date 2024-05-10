/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.apache_commons_csv;

import fr.dademo.batch.resources.BaseWrappedRowResource;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.apache.commons.csv.CSVRecord;

public class ApacheCommonsCsvWrappedRowResource extends BaseWrappedRowResource {

    @Nonnull
    private final CSVRecord delegate;

    public ApacheCommonsCsvWrappedRowResource(@Nonnull CSVRecord delegate) {

        this.delegate = delegate;
        setColumnsIndexMapping(delegate.getParser().getHeaderMap());
    }

    @Nullable
    @Override
    public String getString(int columnIndex) {
        return delegate.get(columnIndex);
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {
        return delegate.get(columnLabel);
    }
}
