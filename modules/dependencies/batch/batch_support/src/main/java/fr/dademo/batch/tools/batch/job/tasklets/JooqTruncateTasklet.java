/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.tasklets;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.CustomTable;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import jakarta.annotation.Nonnull;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class JooqTruncateTasklet<R extends TableRecord<R>> implements Tasklet {

    private final DSLContext dslContext;
    private final Table<R> databaseTable;

    public JooqTruncateTasklet(@Nonnull DSLContext dslContext, @Nonnull CustomTable<R> databaseTable) {
        this.dslContext = dslContext;
        this.databaseTable = databaseTable;
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution, @Nonnull ChunkContext chunkContext) {

        final var failed = new AtomicBoolean(false);
        log.info("Truncating the target table {}", databaseTable);
        dslContext.transaction(configuration -> {

            try {
                final var batchResult = dslContext
                    .truncate(databaseTable)
                    .execute();
                log.info("{} rows affected", batchResult);
            } catch (DataAccessException ex) {
                log.error("An error occurred while truncating the table", ex);
                failed.set(true);
            }
        });

        contribution.setExitStatus(failed.get() ? ExitStatus.FAILED : ExitStatus.COMPLETED);
        return RepeatStatus.FINISHED;
    }
}
