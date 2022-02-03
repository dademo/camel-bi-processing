/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model.database;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseSchemaDefaultImpl;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescriptionBaseDefaultImpl;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Date;
import java.util.List;

/**
 * @author dademo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DatabaseDescriptionDefaultImpl extends DataBackendDescriptionBaseDefaultImpl implements DatabaseDescription {

    @Nullable
    @Size(min = 1, max = 255)
    private String name;

    @Nullable
    @Min(0)
    private Long commitCounts;

    @Nullable
    @Min(0)
    private Long rollbacksCounts;

    @Nullable
    @Min(0)
    private Long bufferBlocksRead;

    @Nullable
    @Min(0)
    private Long diskBlocksRead;

    @Nullable
    @Min(0)
    private Long returnedRowsCount;

    @Nullable
    @Min(0)
    private Long fetchedRowsCount;

    @Nullable
    @Min(0)
    private Long insertedRowsCount;

    @Nullable
    @Min(0)
    private Long updatedRowsCount;

    @Nullable
    @Min(0)
    private Long deletedRowsCount;

    @Nullable
    @Min(0)
    private Long conflictsCount;

    @Nullable
    @Min(0)
    private Long deadlocksCount;

    @Nullable
    @Min(0)
    Long memoryUsageBytes;

    @Nullable
    @Min(0)
    Long cpuUsageMilliCPU;

    @Nullable
    private Duration readTime;

    @Nullable
    private Duration writeTime;

    @Nullable
    private Date lastStatisticsResetTime;

    @NonNull
    @JsonDeserialize(as = List.class, contentAs = DatabaseSchemaDefaultImpl.class)
    private Iterable<DatabaseSchema> databaseSchemas;
}
