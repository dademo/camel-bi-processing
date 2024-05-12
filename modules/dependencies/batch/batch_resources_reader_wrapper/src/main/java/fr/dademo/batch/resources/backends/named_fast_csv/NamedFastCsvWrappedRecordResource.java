/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.named_fast_csv;

import de.siegmar.fastcsv.reader.CsvRecord;
import de.siegmar.fastcsv.reader.NamedCsvRecord;
import fr.dademo.batch.resources.BaseWrappedRowResource;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

@SuppressWarnings("unused")
public class NamedFastCsvWrappedRecordResource extends BaseWrappedRowResource {

    @Nonnull
    private final CsvRecord delegate;

    public NamedFastCsvWrappedRecordResource(@Nonnull NamedCsvRecord delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {
        return delegate.getField(delegate.getFields().indexOf(columnLabel));
    }
}
