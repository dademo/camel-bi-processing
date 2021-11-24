/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.tools.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author dademo
 */
public class NoActionItemWriter<T> implements ItemWriter<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoActionItemWriter.class);

    boolean printWrite;

    public NoActionItemWriter() {
        this(false);
    }

    public NoActionItemWriter(boolean printWrite) {
        this.printWrite = printWrite;
    }

    @Override
    public void write(@Nonnull List<? extends T> items) {

        if (printWrite) {
            LOGGER.info("Writing {} items", items.size());
        }
    }
}
