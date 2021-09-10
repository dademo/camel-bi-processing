package fr.dademo.bi.companies.components.camel.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.TeeInputStream;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheTools {

    private static final String PREFIX = "bi-cache";
    private static final String LOCK_FILE_NAME = "index.lock";
    private static final String RESOURCES_DIRECTORY_NAME = "resources";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String HASH_ALGORITHM = "MD5";
    private static final MessageDigest HASH_COMPUTER;

    static {
        try {
            HASH_COMPUTER = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);  // NOSONAR
        }
    }

    @SneakyThrows
    public static boolean hasCachedInputStream(@NonNull String inputFileIdentifier,
                                               @NonNull Path cacheDirectoryRoot) {

        ensureCacheDirectoryResourcesExists(cacheDirectoryRoot);
        // Locking index file
        try (FileChannel channel = FileChannel.open(lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            return readLockFile(cacheDirectoryRoot)
                    .containsKey(inputFileIdentifier);
        }
    }

    @SneakyThrows
    public static void readFromCachedInputStream(@NonNull String inputFileIdentifier,
                                                 @NonNull Path cacheDirectoryRoot,
                                                 @NonNull Consumer<InputStream> resultConsumer) {

        ensureCacheDirectoryResourcesExists(cacheDirectoryRoot);
        Path cachedFilePath;

        // Locking index file
        try (FileChannel channel = FileChannel.open(lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            cachedFilePath = Path.of(
                    cacheDirectoryRoot.toString(),
                    RESOURCES_DIRECTORY_NAME,
                    Optional.ofNullable(readLockFile(cacheDirectoryRoot).get(inputFileIdentifier))
                            .orElseThrow(() -> new RuntimeException(String.format("Missing cache file for identifier %s", inputFileIdentifier)))
            );
        }

        try (var fileInputStream = new FileInputStream(cachedFilePath.toFile())) {
            resultConsumer.accept(fileInputStream);
        }
    }

    @SneakyThrows
    public static void consumeAndCacheInputStream(@NonNull InputStream inputStream,
                                                  @NonNull String inputFileIdentifier,
                                                  @NonNull Path cacheDirectoryRoot,
                                                  @NonNull Consumer<InputStream> resultConsumer) {

        ensureCacheDirectoryResourcesExists(cacheDirectoryRoot);
        // Writing body to a temp file
        var tempFilePath = Files.createTempFile(PREFIX, "");
        try (var cacheFile = new FileOutputStream(tempFilePath.toFile())) {
            resultConsumer.accept(new TeeInputStream(inputStream, cacheFile));
            cacheFile.flush();
        }
        // Persisting cache
        persistCache(tempFilePath, inputFileIdentifier, cacheDirectoryRoot);
    }

    @SneakyThrows
    private static synchronized void persistCache(Path tempCachedFile,
                                                  String inputFileIdentifier,
                                                  Path cacheDirectoryRoot) {

        var finalFileName = DatatypeConverter
                .printHexBinary(HASH_COMPUTER.digest(inputFileIdentifier.getBytes()))
                .toUpperCase();

        // Locking index file
        try (FileChannel channel = FileChannel.open(lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            var lockFileContent = lockFileMapUsingDirectory(cacheDirectoryRoot);
            lockFileContent.put(inputFileIdentifier, finalFileName);

            FileUtils.moveFile(
                    tempCachedFile.toFile(),
                    Path.of(cacheDirectoryRoot.toString(), RESOURCES_DIRECTORY_NAME, finalFileName).toFile()
            );
            persistLockFile(cacheDirectoryRoot, lockFileContent);
        }
    }

    private static Map<String, String> lockFileMapUsingDirectory(Path cacheDirectoryRoot) {

        return Files.exists(lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot)) ?
                readLockFile(cacheDirectoryRoot) :
                new HashMap<>();
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private static Map<String, String> readLockFile(Path cacheDirectoryRoot) {
        try {
            return MAPPER.readValue(
                    lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot).toFile(),
                    Map.class
            );
        } catch (MismatchedInputException ex) {
            return new HashMap<>();
        }
    }

    @SneakyThrows
    private static void persistLockFile(Path cacheDirectoryRoot, Map<String, String> lockFileContent) {

        MAPPER.writeValue(
                lockFilePathUsingCacheDirectoryRoot(cacheDirectoryRoot).toFile(),
                lockFileContent
        );
    }

    private static Path lockFilePathUsingCacheDirectoryRoot(Path cacheDirectoryRoot) {
        return Path.of(cacheDirectoryRoot.toString(), LOCK_FILE_NAME);
    }

    private static void ensureCacheDirectoryResourcesExists(Path cacheDirectoryRoot) {

        var cacheDirectoryResourcesName = Path.of(cacheDirectoryRoot.toString(), RESOURCES_DIRECTORY_NAME);
        if (!Files.exists(cacheDirectoryResourcesName)) {
            cacheDirectoryResourcesName.toFile().mkdirs();
        } else if (!Files.isDirectory(cacheDirectoryResourcesName)) {
            throw new RuntimeException(String.format("File %s is not a directory", cacheDirectoryResourcesName));
        }
    }
}
