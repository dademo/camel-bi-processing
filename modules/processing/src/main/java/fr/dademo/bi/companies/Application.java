/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies;

import fr.dademo.bi.companies.services.AppJobLauncher;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

/**
 * @author dademo
 */
@SpringBootApplication(exclude = {
    R2dbcAutoConfiguration.class,
    DataSourceAutoConfiguration.class,
    MongoAutoConfiguration.class,
    CouchbaseAutoConfiguration.class,
    KafkaAutoConfiguration.class,
    RabbitAutoConfiguration.class,
    FlywayAutoConfiguration.class,
})
@ImportAutoConfiguration
@EnableBatchProcessing
public class Application implements CommandLineRunner {

    @Autowired
    private AppJobLauncher appJobLauncher;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(Application.class, args)));
    }

    @Override
    public void run(String... args) {
        appJobLauncher.runAll();
    }
}