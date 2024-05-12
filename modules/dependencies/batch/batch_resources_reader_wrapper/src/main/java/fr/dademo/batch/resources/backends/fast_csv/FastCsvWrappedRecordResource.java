/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.fast_csv;

import de.siegmar.fastcsv.reader.CsvRecord;
import fr.dademo.batch.resources.BaseWrappedRowResource;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;

@SuppressWarnings("unused")
public class FastCsvWrappedRecordResource extends BaseWrappedRowResource {

    @Nonnull
    private final CsvRecord delegate;

    public FastCsvWrappedRecordResource(@Nonnull CsvRecord delegate, @Nullable Map<String, Integer> columnsIndexMapping) {

        this.delegate = delegate;
        setColumnsIndexMapping(columnsIndexMapping);
    }

    @Nullable
    @Override
    public String getString(int columnIndex) {
        return delegate.getField(columnIndex - 1);
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {

        return delegate.getField(
            getColumnIndexByName(columnLabel)
        );
    }
}
