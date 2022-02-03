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

package fr.dademo.supervision.job.task.standalone;

import fr.dademo.supervision.dependencies.backends.model.DataBackendStateFetchService;
import fr.dademo.supervision.dependencies.persistence.PersistenceBeans;
import fr.dademo.supervision.dependencies.persistence.services.DataBackendPersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dademo
 */
@Slf4j
@EnableTask
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "fr.dademo.supervision")
public class TaskApplication implements CommandLineRunner {

    @Autowired
    private DataBackendStateFetchService dataBackendStateFetchService;

    @Autowired
    private DataBackendPersistenceService dataBackendPersistenceService;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TaskApplication.class, args)));
    }

    @Transactional(PersistenceBeans.PERSISTENCE_TRANSACTION_MANAGER_BEAN_NAME)
    @Override
    public void run(String... args) {

        log.info("Getting values from backend");
        final var moduleMetaData = dataBackendStateFetchService.getModuleMetaData();
        final var dataBackendDescription = dataBackendStateFetchService.getDataBackendDescription();

        log.info("Persisting values");
        dataBackendPersistenceService.persistBackendFetchResult(moduleMetaData, dataBackendDescription);

        log.info("Task finished");
    }
}
