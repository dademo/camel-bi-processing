/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fr.dademo.data.definitions.DataSetResourceChecksum;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dademo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataGouvFrDataSetResourceChecksum implements DataSetResourceChecksum {

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
