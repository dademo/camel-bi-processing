/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionItemWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionRecord;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Field;
import org.jooq.Insert;
import org.springframework.batch.item.Chunk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.DEFAULT_NAF_DEFINITION_TABLE;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + NAF_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class NafDefinitionJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<NafDefinition, NafDefinitionRecord> implements NafDefinitionItemWriter {

    private final NafDefinitionTable nafDefinitionTable;

    public NafDefinitionJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {
        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(NAF_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(NAF_JOB_NAME))
            )
        );
        this.nafDefinitionTable = getTargetSchemaUsingConfiguration(NAF_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(NafDefinitionTable::new)
            .orElse(DEFAULT_NAF_DEFINITION_TABLE);
    }

    @SneakyThrows
    @Override
    public void write(Chunk<? extends NafDefinition> items) {

        log.info("Writing {} naf definition documents", items.size());
        synchronized (getDslContext()) {
            performBulkWrite(items);
        }
    }

    @Override
    protected Insert<NafDefinitionRecord> getInsertStatement() {

        return getDslContext().insertInto(nafDefinitionTable,
                DEFAULT_NAF_DEFINITION_TABLE.FIELD_NAF_CODE,
                DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE,
                DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_65,
                DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_40
            ).values((String) null, null, null, null)
            .onConflict(DEFAULT_NAF_DEFINITION_TABLE.FIELD_NAF_CODE)
            .doUpdate()
            .set(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE, asExcludedField(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE))
            .set(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_65, asExcludedField(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_65))
            .set(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_40, asExcludedField(DEFAULT_NAF_DEFINITION_TABLE.FIELD_TITLE_40));
    }

    @Override
    protected Consumer<NafDefinition> bindToStatement(BatchBindStep statement) {

        return nafDefinition -> statement.bind(
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
