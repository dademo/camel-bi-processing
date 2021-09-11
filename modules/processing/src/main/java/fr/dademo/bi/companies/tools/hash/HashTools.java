package fr.dademo.bi.companies.tools.hash;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.security.MessageDigest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashTools {

    @SneakyThrows
    public static MessageDigest getHashComputerForAlgorithm(String algorithm) {
        return MessageDigest.getInstance(algorithm);
    }

    @SneakyThrows
    public static String computeHash(MessageDigest hashComputer, InputStream in) {

        var buffer = new byte[4096];
        int read = 0;

        try (in) {
            while (read != -1) {
                read = in.read(buffer);
                hashComputer.update(buffer, 0, read);
            }
        }

        return DatatypeConverter
                .printHexBinary(hashComputer.digest(hashComputer.digest()))
                .toUpperCase();
    }
}
