/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.model.database;

import fr.dademo.supervision.backends.model.database.resources.DatabaseConnection;
import fr.dademo.supervision.backends.model.database.resources.DatabaseSchema;
import fr.dademo.supervision.backends.model.shared.DataBackendDescriptionDefaultImpl;
import fr.dademo.supervision.backends.model.shared.DataBackendKind;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Date;

/**
 * @author dademo
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DatabaseDescriptionDefaultImpl extends DataBackendDescriptionDefaultImpl implements DatabaseDescription {

    // pg_postmaster_start_time
    @Nullable
    private Date startTime;

    @Nonnull
    @Size(min = 1)
    private String name;

    @Nullable
    @Min(0)
    private Long memoryUsageBytes;

    @Nullable
    @Min(0)
    private Long cpuUsageMilliCPU;

    /* https://www.postgresql.org/docs/9.2/monitoring-stats.html#PG-STAT-DATABASE-VIEW */
    @Nullable
    @Min(0)
    private Long commitCounts;

    @Nullable
    @Min(0)
    private Long rollbackCounts;

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
    private Duration readTime;

    @Nullable
    private Duration writeTime;

    @Nullable
    private Date lastStatisticsResetTime;

    @Nullable
    private Iterable<DatabaseConnection> databaseConnections;

    @Nullable
    private Iterable<DatabaseSchema> databaseSchemas;

    @Nonnull
    @Override
    public final DataBackendKind getKind() {
        return DataBackendKind.DATABASE;
    }
}
