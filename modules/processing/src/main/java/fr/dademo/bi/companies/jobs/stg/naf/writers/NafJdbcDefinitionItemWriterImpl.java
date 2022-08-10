/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionItemWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionRecord;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.NAF_DEFINITION;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author dademo
 */
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + NAF_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class NafJdbcDefinitionItemWriterImpl implements NafDefinitionItemWriter {

    @Autowired
    @Qualifier(STG_DATA_SOURCE_DSL_CONTEXT_BEAN_NAME)
    @Getter
    private DSLContext dslContext;

    @SneakyThrows
    @Override
    public void write(List<? extends NafDefinition> items) {

        log.info("Writing {} naf definition documents", items.size());

        try (final var insertStatement = getInsertStatement()) {

            final var batchInsertStatement = dslContext.batch(insertStatement);

            items.stream()
                .map(this::nafDefinitionBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

            synchronized (this) {
                final var batchResult = batchInsertStatement.execute();
                if (batchResult.length > 0) {
                    final int totalUpdated = Arrays.stream(batchResult).sum();
                    log.info("{} rows affected", totalUpdated);
                } else {
                    log.error("An error occurred while running batch");
                }
            }
        }
    }

    @SuppressWarnings("resource")
    private InsertOnDuplicateSetMoreStep<NafDefinitionRecord> getInsertStatement() {

        return dslContext.insertInto(NAF_DEFINITION,
                NAF_DEFINITION.FIELD_NAF_CODE,
                NAF_DEFINITION.FIELD_TITLE,
                NAF_DEFINITION.FIELD_TITLE_65,
                NAF_DEFINITION.FIELD_TITLE_40
            ).values((String) null, null, null, null)
            .onConflict(NAF_DEFINITION.FIELD_NAF_CODE)
            .doUpdate()
            .set(NAF_DEFINITION.FIELD_TITLE, asExcludedField(NAF_DEFINITION.FIELD_TITLE))
            .set(NAF_DEFINITION.FIELD_TITLE_65, asExcludedField(NAF_DEFINITION.FIELD_TITLE_65))
            .set(NAF_DEFINITION.FIELD_TITLE_40, asExcludedField(NAF_DEFINITION.FIELD_TITLE_40));
    }

    private Consumer<BatchBindStep> nafDefinitionBind(NafDefinition nafDefinition) {

        return items -> items.bind(
            nafDefinition.getNafCode(),
            nafDefinition.getTitle(),
            nafDefinition.getTitle65(),
            nafDefinition.getTitle40()
        );
    }

    private <T> Field<T> asExcludedField(@Nonnull Field<T> field) {
        return field(name("excluded", field.getName()), field.getType());
    }
}
