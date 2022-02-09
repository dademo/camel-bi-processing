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
import fr.dademo.tools.tools.HashTools;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;

/**
 * @author dademo
 */
@Slf4j
@EnableTask
@SpringBootApplication(scanBasePackages = "fr.dademo.supervision")
public class TaskApplication implements CommandLineRunner {

    public static final String HASH_ALGORITHM = "SHA3-256";
    public static final String COMPRESSION_ALGORITHM = CompressorStreamFactory.XZ;
    public static final String HEADER_MESSAGE_HASH = "x-hash";
    public static final String HEADER_MESSAGE_HASH_ALGORITHM = "x-hash-algorithm";
    public static final String HEADER_COMPRESSION_ALGORITHM = "x-compression-algorithm";
    public static final int DEFAULT_BUFFER_SIZE = 4096;
    public static final int XZ_USED_PRESET = 9;

    @Autowired
    private JobOutputConfiguration jobOutputConfiguration;

    @Autowired
    private DataBackendStateFetchService dataBackendStateFetchService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
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
        final var messageBody = dataBackendStateFetchServiceExecutionResultAsBytes(
            DataBackendStateFetchServiceExecutionResult.builder()
                .moduleMetaData(moduleMetaData)
                .dataBackendDescription(dataBackendDescription)
                .build()
        );

        amqpTemplate.send(
            jobOutputConfiguration.getExchangeName(),
            Optional.ofNullable(jobOutputConfiguration.getRoutingKey()).orElse("#"),
            MessageBuilder
                .withBody(compressMessageBody(messageBody))
                .setHeader(HEADER_COMPRESSION_ALGORITHM, COMPRESSION_ALGORITHM)
                .setHeader(HEADER_MESSAGE_HASH, computeHashString(messageBody))
                .setHeader(HEADER_MESSAGE_HASH_ALGORITHM, HASH_ALGORITHM)
                .setTimestampIfAbsent(new Date(System.currentTimeMillis()))
                .build()
        );

        log.info("Task finished");
    }

    @SneakyThrows
    private byte[] compressMessageBody(byte[] in) {

        try (final var byteOuputStream = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE)) {
            try (final var compressorOutputStream = new XZCompressorOutputStream(byteOuputStream, XZ_USED_PRESET)) {

                compressorOutputStream.write(in);
                compressorOutputStream.flush();
                compressorOutputStream.finish();
            }
            return byteOuputStream.toByteArray();
        }
    }

    @SneakyThrows
    private byte[] dataBackendStateFetchServiceExecutionResultAsBytes(DataBackendStateFetchServiceExecutionResult dataBackendStateFetchServiceExecutionResult) {
        return objectMapper.writeValueAsBytes(dataBackendStateFetchServiceExecutionResult);
    }

    private String computeHashString(byte[] in) {
        return HashTools.computeHashString(HashTools.getHashComputerForAlgorithm(HASH_ALGORITHM), in);
    }
}
