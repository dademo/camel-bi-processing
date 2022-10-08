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

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.tasklets;

import fr.dademo.batch.beans.jdbc.tools.LiquibaseMigrationsSupplier;
import fr.dademo.batch.configuration.BatchConfiguration;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Slf4j
public class LiquibaseMigrationTasklet implements Tasklet {

    final LiquibaseMigrationsSupplier migrationsSupplier;

    @Builder
    public LiquibaseMigrationTasklet(@NotEmpty String migrationFolder,
                                     @Nonnull BatchConfiguration.JobDataSourceConfiguration jobDataSourceConfiguration,
                                     @NotEmpty String databaseCatalog,
                                     @NotEmpty String databaseSchema,
                                     @Nonnull DataSource dataSource,
                                     @Nonnull ResourceLoader resourceLoader) {

        migrationsSupplier = LiquibaseMigrationsSupplier.builder()
            .migrationFolder(migrationFolder)
            .changeLogFileName(
                Optional.ofNullable(jobDataSourceConfiguration.getChangeLogFileName())
                    .filter(v -> !v.isEmpty())
                    .orElse(BatchConfiguration.JobConfiguration.DEFAULT_CHANGELOG_FILE)
            )
            .databaseCatalog(databaseCatalog)
            .databaseSchema(databaseSchema)
            .contexts(jobDataSourceConfiguration.getMigrationContexts())
            .dataSource(dataSource)
            .resourceLoader(resourceLoader)
            .build();
    }

    @Override
    public RepeatStatus execute(@Nonnull StepContribution contribution, @Nonnull ChunkContext chunkContext) {

        log.info("Running migrations");
        migrationsSupplier.applyMigrations();
        log.info("Migration completed successfully");

        // Migration were performed, no more runs required
        return RepeatStatus.FINISHED;
    }
}
