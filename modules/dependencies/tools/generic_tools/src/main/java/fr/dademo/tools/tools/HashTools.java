package fr.dademo.tools.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashTools {

    @SneakyThrows
    public static MessageDigest getHashComputerForAlgorithm(String algorithm) {
        return MessageDigest.getInstance(algorithm);
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
