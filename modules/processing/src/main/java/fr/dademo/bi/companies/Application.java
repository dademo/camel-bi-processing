/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies;

import fr.dademo.batch.services.AppJobLauncher;
import fr.dademo.bi.companies.exceptions.ApplicationInitializationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import java.util.stream.Collectors;

/**
 * @author dademo
 */
@Slf4j
@SpringBootApplication(exclude = {
    BatchAutoConfiguration.class,
    SqlInitializationAutoConfiguration.class,
    R2dbcAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    MongoAutoConfiguration.class,
    CouchbaseAutoConfiguration.class,
    KafkaAutoConfiguration.class,
    RabbitAutoConfiguration.class,
    FlywayAutoConfiguration.class,
    LiquibaseAutoConfiguration.class,
})
@ImportAutoConfiguration
//@EnableAutoConfiguration
@EnableJdbcRepositories
//@EnableBatchProcessing
public class Application implements ApplicationRunner, ExitCodeGenerator {

    private final AppJobLauncher appJobLauncher;

    private boolean successful;

    public Application(AppJobLauncher appJobLauncher) {
        this.appJobLauncher = appJobLauncher;
        this.successful = false;
    }

    public static void main(String... args) {

        System.exit(SpringApplication.exit(
            new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args)
        ));
    }

    @Override
    public void run(ApplicationArguments args) {

        try {
            final var applicationParsedArguments = ApplicationParsedArguments.usingApplicationArguments(args);

            if (applicationParsedArguments.isHelp()) {

                ApplicationParsedArguments.help();
                successful = true;
                return;
            }

            if (applicationParsedArguments.isListJobs()) {
                listJobs();
                successful = true;
                return;
            }

            log.info("Running all jobs");
            successful = appJobLauncher.run(
                applicationParsedArguments.getOnly(),
                applicationParsedArguments.isForce()
            );
            log.info("Job finished");
        } catch (ApplicationInitializationError ex) {
            ApplicationParsedArguments.help(ex.getMessage());
        }
    }

    private void listJobs() {

        final var foundJobs = appJobLauncher.getAllAvailableJobs();

        log.info("Found {} jobs :\n{}",
            foundJobs.size(),
            foundJobs.stream()
                .map("  - %s"::formatted)
                .collect(Collectors.joining("\n"))
        );
    }

    @Override
    public int getExitCode() {
        return successful ? 0 : 1;
    }
}