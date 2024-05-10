/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.persistence_service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.supervision.dependencies.backends.model.DataBackendStateFetchServiceExecutionResult;
import fr.dademo.supervision.dependencies.persistence.services.DataBackendPersistenceService;
import fr.dademo.supervision.job.persistence_service.consumer.exception.InconsistentMessageException;
import fr.dademo.tools.tools.HashTools;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Service
public class MessageConsumerService {

    public static final String HEADER_MESSAGE_HASH = "x-hash";
    public static final String HEADER_MESSAGE_HASH_ALGORITHM = "x-hash-algorithm";
    public static final String HEADER_COMPRESSION_ALGORITHM = "x-compression-algorithm";

    private final DataBackendPersistenceService dataBackendPersistenceService;
    private final ObjectMapper objectMapper;


    public MessageConsumerService(@Nonnull DataBackendPersistenceService dataBackendPersistenceService,
                                  @Nonnull ObjectMapper objectMapper) {
        this.dataBackendPersistenceService = dataBackendPersistenceService;
        this.objectMapper = objectMapper;
    }

    @Bean
    Consumer<Message<byte[]>> supervision() {
        return this::consumeSupervisionMessage;
    }

    @Bean
    private void consumeSupervisionMessage(Message<byte[]> message) {

        DataBackendStateFetchServiceExecutionResult dataBackendStateFetchServiceExecutionResult;

        log.info("Received message published at [{}]",
            Optional.ofNullable(message.getHeaders().getTimestamp())
                .map(Date::new)
                .map(Date::toString)
                .orElse("-")
        );

        final var compressionAlgorithm = Optional.ofNullable(message.getHeaders().get(HEADER_COMPRESSION_ALGORITHM, String.class))
            .orElseThrow(() -> InconsistentMessageException.forInconsistentHeaderField(HEADER_COMPRESSION_ALGORITHM));
        final var messageHash = Optional.ofNullable(message.getHeaders().get(HEADER_MESSAGE_HASH, String.class))
            .orElseThrow(() -> InconsistentMessageException.forInconsistentHeaderField(HEADER_MESSAGE_HASH));
        final var messageDigest = HashTools.getHashComputerForAlgorithm(
            Optional.ofNullable(message.getHeaders().get(HEADER_MESSAGE_HASH_ALGORITHM, String.class))
                .orElseThrow(() -> InconsistentMessageException.forInconsistentHeaderField(HEADER_MESSAGE_HASH_ALGORITHM))
        );

        final var decompressedMessagePayload = decompress(compressionAlgorithm, message.getPayload());

        // Validating hash
        if (!messageHash.equals(HashTools.computeHashString(messageDigest, decompressedMessagePayload))) {
            throw InconsistentMessageException.withMessage("Message hash does not match decompressed payload");
        }

        // Mapping values
        try {
            dataBackendStateFetchServiceExecutionResult = objectMapper.readValue(
                decompressedMessagePayload,
                DataBackendStateFetchServiceExecutionResult.class
            );
        } catch (Exception e) {
            throw InconsistentMessageException.withMessage("unable to parse emitted values", e);
        }

        // Saving values
        dataBackendPersistenceService.persistBackendFetchResult(
            dataBackendStateFetchServiceExecutionResult.getModuleMetaData(),
            dataBackendStateFetchServiceExecutionResult.getDataBackendDescription()
        );

        log.info("Values persisted");
    }

    @SneakyThrows
    private byte[] decompress(String algorithm, byte[] in) {

        try (final var inputStream = new ByteArrayInputStream(in)) {
            try (final var compressorInputStream = new CompressorStreamFactory().createCompressorInputStream(algorithm, inputStream)) {
                return IOUtils.toByteArray(compressorInputStream);
            }
        }
    }
}
