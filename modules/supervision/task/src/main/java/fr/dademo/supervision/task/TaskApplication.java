/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task;

import fr.dademo.supervision.backends.model.DataBackendStateFetchService;
import fr.dademo.supervision.task.services.DataBackendPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dademo
 */
@Slf4j
@EnableTask
@SpringBootApplication(scanBasePackages = "fr.dademo.supervision")
public class TaskApplication implements CommandLineRunner {

    @Autowired
    private DataBackendStateFetchService dataBackendStateFetchService;

    @Autowired
    private DataBackendPersistenceService dataBackendPersistenceService;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TaskApplication.class, args)));
    }

    @Transactional
    @Override
    public void run(String... args) {

        log.info("Getting values from backend");
        final var moduleMetaData = dataBackendStateFetchService.getModuleMetaData();
        final var dataBackendDescription = dataBackendStateFetchService.getDataBackendDescription();

        //dataBackendPersistenceService.persistBackendFetchResult();

        log.info("Mapping values");

        log.info("Persisting values");

        log.info("Task finished");
    }
}
