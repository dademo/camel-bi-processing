/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationRecord;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.batch.item.Chunk;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable.DEFAULT_ASSOCIATION_TABLE;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + ASSOCIATION_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class AssociationJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<Association, AssociationRecord> implements AssociationItemWriter {

    private final AssociationTable associationTable;

    public AssociationJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(ASSOCIATION_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(ASSOCIATION_JOB_NAME))
            )
        );
        this.associationTable = getTargetSchemaUsingConfiguration(ASSOCIATION_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(AssociationTable::new)
            .orElse(DEFAULT_ASSOCIATION_TABLE);
    }

    @Override
    public void write(Chunk<? extends Association> items) {

        log.info("Writing {} association documents", items.size());
        performBulkWrite(items);
    }

    @Override
    protected Insert<AssociationRecord> getInsertStatement() {

        return getDslContext().insertInto(associationTable,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ID,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ID_EX,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_SIRET,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_GESTION,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_CREATION_DATE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_PUBLICATION_DATE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_NATURE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_GROUPEMENT,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_TITLE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_OBJECT,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_1,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_2,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_3,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_LEADER_CIVILITY,
            //ASSOCIATION.FIELD_ASSOCIATION_PHONE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_WEBSITE,
            //ASSOCIATION.FIELD_ASSOCIATION_EMAIL,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_OBSERVATION,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_POSITION,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_RUP_CODE,
            DEFAULT_ASSOCIATION_TABLE.FIELD_ASSOCIATION_LAST_UPDATED
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, /*null,*/ null, /*null,*/ null, null, null, null);
    }

    protected Consumer<Association> bindToStatement(BatchBindStep statement) {

        return association -> statement.bind(
            association.getId(),
            association.getIdEx(),
            association.getSiret(),
            association.getGestion(),
            association.getCreationDate(),
            association.getPublicationDate(),
            association.getNature(),
            association.getGroupement(),
            association.getTitle(),
            association.getObject(),
            association.getSocialObject1(),
            association.getSocialObject2(),
            association.getAddress1(),
            association.getAddress2(),
            association.getAddress3(),
            association.getAddressPostalCode(),
            association.getAddressInseeCode(),
            association.getAddressCityLibelle(),
            association.getLeaderCivility(),
            //association.getTelephone(),
            association.getWebsite(),
            //association.getEmail(),
            association.getObservation(),
            association.getPosition(),
            association.getRupCode(),
            association.getLastUpdated()
        );
    }
}
