/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies;

import fr.dademo.batch.services.AppJobLauncher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
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
//@EnableBatchProcessing
public class Application implements CommandLineRunner, ExitCodeGenerator {

    @Autowired
    private AppJobLauncher appJobLauncher;

    private boolean successfull = false;

    public static void main(String[] args) {

        System.exit(SpringApplication.exit(
            new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.NONE)
                .run(args)
        ));
    }

    @Override
    public void run(String... args) {
        log.info("Running all jobs");
        successfull = appJobLauncher.runAll();
        log.info("Job finished");
    }

    @Override
    public int getExitCode() {
        return successfull ? 0 : 1;
    }
}