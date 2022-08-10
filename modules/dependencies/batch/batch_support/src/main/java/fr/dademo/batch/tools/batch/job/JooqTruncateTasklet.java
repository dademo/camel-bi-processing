/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.TableRecord;
import org.jooq.impl.CustomTable;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import javax.annotation.Nonnull;

@Slf4j
public class JooqTruncateTasklet<R extends TableRecord<R>> implements Tasklet {

    private final DSLContext dslContext;
    private final CustomTable<R> databaseTable;

    public JooqTruncateTasklet(@Nonnull DSLContext dslContext, @Nonnull CustomTable<R> databaseTable) {
        this.dslContext = dslContext;
        this.databaseTable = databaseTable;
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution, @Nonnull ChunkContext chunkContext) {

        log.info("Truncating the target table {}", databaseTable);
        try (final var statement = dslContext.truncate(databaseTable)) {
            final var batchResult = statement.execute();
            log.info("{} rows affected", batchResult);
        }

        return RepeatStatus.FINISHED;
    }
}
