package fr.dademo.tools.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTools {

    private static final Map<String, String> WELL_KNOWN_ALGORITHMS = Map.of(
            "MD5", "MD5",
            "SHA1", "SHA-1",
            "SHA256", "SHA-256",
            "SHA512", "SHA-512",
            "SHA3-256", "SHA3-256",
            "SHA3-512", "SHA3-512"
    );

    @SneakyThrows
    public static MessageDigest getHashComputerForAlgorithm(String algorithm) {
        return MessageDigest.getInstance(WELL_KNOWN_ALGORITHMS.getOrDefault(algorithm.toUpperCase(), algorithm));
    }

    public static byte[] computeHash(MessageDigest hashComputer, InputStream in) throws IOException {

        final var buffer = new byte[1048576];
        int read;

        try (in) {
            while ((read = in.read(buffer)) != -1) {
                hashComputer.update(buffer, 0, read);
            }
        }

        return hashComputer.digest();
    }

    public static byte[] computeHash(MessageDigest hashComputer, byte[] in) {
        return hashComputer.digest(in);
    }

    public static String computeHashString(MessageDigest hashComputer, InputStream in) throws IOException {
        return Hex.encodeHexString(computeHash(hashComputer, in));
    }

    @SneakyThrows
    public static String computeHashString(MessageDigest hashComputer, byte[] in) {
        return Hex.encodeHexString(computeHash(hashComputer, in));
    }
}
