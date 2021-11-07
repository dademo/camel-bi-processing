package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataGouvFrDataSetResourceChecksum {

    @Nonnull
    private String type;

    @Nonnull
    private String value;

    @AllArgsConstructor
    public enum DataGouvFrDataSetResourceChecksumType {
        SHA1("sha1"),
        SHA2("sha2"),
        SHA256("sha256"),
        MD5("md5"),
        CRC("crc");

        private final String value;

        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
