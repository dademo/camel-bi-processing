/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.reader;

import lombok.SneakyThrows;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import javax.annotation.Nonnull;
import java.io.InputStream;

/**
 * @author dademo
 */
public abstract class UnidirectionalItemStreamReaderSupport<T> implements ItemStreamReader<T> {

    @Override
    public void update(@Nonnull ExecutionContext executionContext) throws ItemStreamException {
        // Nothing to do
    }

    @SneakyThrows
    protected void sneakyClose(InputStream inputStream) {
        inputStream.close();
    }
}
