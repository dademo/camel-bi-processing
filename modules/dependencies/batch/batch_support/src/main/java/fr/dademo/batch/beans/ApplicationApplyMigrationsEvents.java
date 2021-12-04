/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.beans;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.beans.jdbc.tools.FlywayMigrationTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author dademo
 */
@Slf4j
@Configuration
public class ApplicationApplyMigrationsEvents {

    @Autowired
    private DataSourcesFactory dataSourcesFactory;

    @EventListener(ApplicationStartedEvent.class)
    @ConditionalOnProperty(value = "flyway.applyMigrations", matchIfMissing = true)
    public void doApplyFlywayMigrations() {

        log.debug("Application started");
        log.info("Applying all migrations");
        dataSourcesFactory.getAllMigrations().forEach(FlywayMigrationTools::applyMigration);
        log.info("All migrations applied successfully");
    }
}
