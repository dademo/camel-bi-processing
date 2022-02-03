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

package fr.dademo.supervision.job.task.light;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.supervision.dependencies.backends.model.DataBackendStateFetchService;
import fr.dademo.supervision.dependencies.backends.model.DataBackendStateFetchServiceExecutionResult;
import fr.dademo.supervision.job.task.light.configuration.JobOutputConfiguration;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

import static fr.dademo.supervision.job.task.light.TaskBeans.OUTPUT_AMQP_TEMPLATE_CONFIG_BEAN_NAME;

/**
 * @author dademo
 */
@Slf4j
@EnableTask
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "fr.dademo.supervision")
public class TaskApplication implements CommandLineRunner {

    @Autowired
    private JobOutputConfiguration jobOutputConfiguration;

    @Autowired
    private DataBackendStateFetchService dataBackendStateFetchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier(OUTPUT_AMQP_TEMPLATE_CONFIG_BEAN_NAME)
    @Getter
    private AmqpTemplate amqpTemplate;

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(TaskApplication.class, args)));
    }

    @Override
    public void run(String... args) {

        log.info("Getting values from backend");
        final var moduleMetaData = dataBackendStateFetchService.getModuleMetaData();
        final var dataBackendDescription = dataBackendStateFetchService.getDataBackendDescription();

        log.info("Sending values");
        amqpTemplate.send(
            jobOutputConfiguration.getExchange(),
            Optional.ofNullable(jobOutputConfiguration.getRoutingKey()).orElse("#"),
            MessageBuilder
                .withBody(
                    dataBackendStateFetchServiceExecutionResultAsBytes(
                        DataBackendStateFetchServiceExecutionResult.builder()
                            .moduleMetaData(moduleMetaData)
                            .dataBackendDescription(dataBackendDescription)
                            .build()
                    ))
                .build()
        );

        log.info("Task finished");
    }

    @SneakyThrows
    private byte[] dataBackendStateFetchServiceExecutionResultAsBytes(DataBackendStateFetchServiceExecutionResult dataBackendStateFetchServiceExecutionResult) {
        return objectMapper.writeValueAsBytes(dataBackendStateFetchServiceExecutionResult);
    }
}
