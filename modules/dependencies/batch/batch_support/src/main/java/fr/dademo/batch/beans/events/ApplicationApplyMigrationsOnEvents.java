/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans.events;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.beans.jdbc.tools.FlywayMigrationTools;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Configuration
public class ApplicationApplyMigrationsOnEvents {

    private final DataSourcesFactory dataSourcesFactory;

    public ApplicationApplyMigrationsOnEvents(@Nonnull DataSourcesFactory dataSourcesFactory) {
        this.dataSourcesFactory = dataSourcesFactory;
    }

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "flyway.applyMigrations", matchIfMissing = true)
    public void doApplyFlywayMigrations() {

        log.info("Applying all migrations");
        dataSourcesFactory.getAllMigrations().forEach(FlywayMigrationTools::applyMigration);
        log.info("All migrations applied successfully");
    }
}
