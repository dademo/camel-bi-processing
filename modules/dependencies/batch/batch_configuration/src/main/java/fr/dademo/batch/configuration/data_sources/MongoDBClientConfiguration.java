/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.configuration.data_sources;

import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.WriteConcern;
import lombok.*;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.Optional;

/**
 * @author dademo
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MongoDBClientConfiguration {

    public static final String DEFAULT_SPRING_APPLICATION_NAME = "JAVA_SPRING";

    @Setter
    @NotBlank
    private String connectionString;

    @Setter
    @NotBlank
    private String database;

    @Setter
    @Nullable
    private String username;

    @Setter
    @Nullable
    private String password;

    @Setter
    @Nullable
    private String applicationName;

    @Nonnull
    private WriteConcern writeConcern = WriteConcern.ACKNOWLEDGED;

    @Nonnull
    private ReadConcern readConcern = ReadConcern.DEFAULT;

    public void setWriteConcern(String writeConcernStr) {
        writeConcern = WriteConcern.valueOf(writeConcernStr);
    }

    public void setReadConcern(String readConcernStr) {

        if (Strings.isBlank(readConcernStr) || "default".equals(readConcernStr)) {
            readConcern = ReadConcern.DEFAULT;
        } else {
            readConcern = new ReadConcern(ReadConcernLevel.valueOf(readConcernStr));
        }
    }

    @NotEmpty
    public String getApplicationName() {

        return Optional.ofNullable(applicationName)
            .filter(Strings::isNotBlank)
            .orElse(DEFAULT_SPRING_APPLICATION_NAME);
    }
}
