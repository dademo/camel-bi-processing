/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.shared;

import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.data_sources.JDBCDataSourceConfiguration;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Insert;
import org.jooq.impl.CustomRecord;
import org.springframework.batch.item.Chunk;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public abstract class AbstractApplicationJdbcWriter<T, R extends CustomRecord<R>> extends BaseJobWriter {

    @Getter(AccessLevel.PROTECTED)
    private final DSLContext dslContext;

    protected AbstractApplicationJdbcWriter(@Nonnull DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    protected static Optional<String> getTargetSchemaUsingConfiguration(@Nonnull String configJobName,
                                                                        @Nonnull BatchConfiguration batchConfiguration,
                                                                        @Nonnull BatchDataSourcesConfiguration batchDataSourcesConfiguration) {

        return getJobOutputDataSourceName(configJobName, batchConfiguration)
            .map(batchDataSourcesConfiguration::getJDBCDataSourceConfigurationByName)
            .map(JDBCDataSourceConfiguration::getSchema);
    }

    @SneakyThrows
    protected void performBulkWrite(Chunk<? extends T> items) {

        getDslContext().transaction(configuration -> {
            final var batchInsertStatement = configuration.dsl().batch(getInsertStatement());
            items.forEach(bindToStatement(batchInsertStatement));

            final var batchResult = batchInsertStatement.execute();
            if (batchResult.length > 0) {
                final int totalUpdated = Arrays.stream(batchResult).sum();
                log.info("{} rows affected", totalUpdated);
            } else {
                log.error("An error occurred while running batch");
            }
        });
    }

    protected abstract Insert<R> getInsertStatement();

    protected abstract Consumer<T> bindToStatement(BatchBindStep statement);
}
