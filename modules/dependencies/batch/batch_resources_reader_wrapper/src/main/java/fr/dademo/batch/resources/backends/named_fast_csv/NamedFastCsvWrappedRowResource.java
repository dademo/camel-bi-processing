/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.resources.backends.named_fast_csv;

import de.siegmar.fastcsv.reader.NamedCsvRow;
import fr.dademo.batch.resources.BaseWrappedRowResource;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NamedFastCsvWrappedRowResource extends BaseWrappedRowResource {

    @Nonnull
    private final NamedCsvRow delegate;

    public NamedFastCsvWrappedRowResource(@Nonnull NamedCsvRow delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public String getString(@Nonnull String columnLabel) {

        return delegate.getField(columnLabel);
    }
}
