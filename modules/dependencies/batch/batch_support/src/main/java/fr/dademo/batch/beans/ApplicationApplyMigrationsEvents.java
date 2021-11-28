/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.beans.jdbc.tools.FlywayMigrationTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@Configuration
public class ApplicationApplyMigrationsEvents {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationApplyMigrationsEvents.class);

    @Autowired
    private DataSourcesFactory dataSourcesFactory;

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "flyway.applyMigrations", matchIfMissing = true)
    public void doApplyFlywayMigrations() {

        LOGGER.debug("Application started");
        LOGGER.info("Applying all migrations");
        dataSourcesFactory.getAllMigrations().forEach(FlywayMigrationTools::applyMigration);
        LOGGER.info("All migrations applied successfully");
    }
}
