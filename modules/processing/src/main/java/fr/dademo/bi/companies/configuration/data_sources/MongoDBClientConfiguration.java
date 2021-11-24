/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.configuration.data_sources;

import com.mongodb.ReadConcern;
import com.mongodb.ReadConcernLevel;
import com.mongodb.WriteConcern;
import lombok.*;
import org.apache.logging.log4j.util.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;

/**
 * @author dademo
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MongoDBClientConfiguration {

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
}
