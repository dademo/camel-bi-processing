package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGouvFrDataSetResourceChecksum {

    @Nonnull
    private String type;

    @Nonnull
    private String value;

    @AllArgsConstructor
    @Getter
    public enum DataGouvFrDataSetResourceChecksumType {
        SHA1("sha1"),
        SHA2("sha2"),
        SHA256("sha256"),
        MD5("md5"),
        CRC("crc");

        private final String value;
    }
}
