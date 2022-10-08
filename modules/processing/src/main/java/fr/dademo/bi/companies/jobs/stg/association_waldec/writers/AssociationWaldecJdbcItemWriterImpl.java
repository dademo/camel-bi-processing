/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec.writers;

import fr.dademo.batch.beans.jdbc.DataSourcesFactory;
import fr.dademo.batch.configuration.BatchConfiguration;
import fr.dademo.batch.configuration.BatchDataSourcesConfiguration;
import fr.dademo.batch.configuration.exception.MissingJobDataSourceConfigurationException;
import fr.dademo.bi.companies.jobs.stg.association_waldec.AssociationWaldecItemWriter;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecRecord;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecTable;
import fr.dademo.bi.companies.shared.AbstractApplicationJdbcWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jooq.BatchBindStep;
import org.jooq.Insert;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.JobDefinition.ASSOCIATION_WALDEC_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.JobDefinition.ASSOCIATION_WALDEC_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecTable.DEFAULT_ASSOCIATION_WALDEC_TABLE;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Slf4j
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + ASSOCIATION_WALDEC_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class AssociationWaldecJdbcItemWriterImpl extends AbstractApplicationJdbcWriter<AssociationWaldec, AssociationWaldecRecord> implements AssociationWaldecItemWriter {

    private final AssociationWaldecTable associationWaldecTable;

    public AssociationWaldecJdbcItemWriterImpl(
        DataSourcesFactory dataSourcesFactory,
        BatchConfiguration batchConfiguration,
        BatchDataSourcesConfiguration batchDataSourcesConfiguration
    ) {

        super(
            dataSourcesFactory.getDslContextByDataSourceName(
                getJobOutputDataSourceName(ASSOCIATION_WALDEC_CONFIG_JOB_NAME, batchConfiguration)
                    .orElseThrow(MissingJobDataSourceConfigurationException.forJob(ASSOCIATION_WALDEC_JOB_NAME))
            )
        );
        this.associationWaldecTable = getTargetSchemaUsingConfiguration(ASSOCIATION_WALDEC_CONFIG_JOB_NAME, batchConfiguration, batchDataSourcesConfiguration)
            .map(AssociationWaldecTable::new)
            .orElse(DEFAULT_ASSOCIATION_WALDEC_TABLE);
    }

    @SneakyThrows
    @Override
    public void write(List<? extends AssociationWaldec> items) {

        log.info("Writing {} company documents", items.size());
        performBulkWrite(items);
    }

    @SuppressWarnings("resource")
    @Override
    protected Insert<AssociationWaldecRecord> getInsertStatement() {

        return getDslContext().insertInto(associationWaldecTable,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ID,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ID_EX,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_SIRET,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_RUP_CODE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_CREATION_DATE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_DECLARATION_DATE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_PUBLICATION_DATE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_DISSOLUTION_DATE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_NATURE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GROUPEMENT,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_TITLE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_TITLE_SHORT,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_OBJECT,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_COMPLEMENT,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_STREET_NUMBER,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_REPETITION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_STREET_TYPE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_STREET_LIBELLE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_DISTRIBUTION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_DECLARANT_SURNAME,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_ADDRESS_COMPLEMENT_ID,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_ADDRESS_COMPLEMENT_GEO,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_ADDRESS_STREET_LIBELLE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_ADDRESS_DISTRIBUTION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_ADDRESS_POSTAL_CODE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_FORWARD,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_GESTION_COUNTRY,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_LEADER_CIVILITY,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_WEBSITE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_PUBLIC_WEBSITE,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_OBSERVATION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_POSITION,
            DEFAULT_ASSOCIATION_WALDEC_TABLE.FIELD_ASSOCIATION_LAST_UPDATED
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null
        );
    }

    @Override
    protected Consumer<AssociationWaldec> bindToStatement(BatchBindStep statement) {

        return associationWaldec -> statement.bind(
            associationWaldec.getId(),
            associationWaldec.getIdEx(),
            associationWaldec.getSiret(),
            associationWaldec.getRupCode(),
            associationWaldec.getGestion(),
            associationWaldec.getCreationDate(),
            associationWaldec.getDeclarationDate(),
            associationWaldec.getPublicationDate(),
            associationWaldec.getDissolutionDate(),
            associationWaldec.getNature(),
            associationWaldec.getGroupement(),
            associationWaldec.getTitle(),
            associationWaldec.getTitleShort(),
            associationWaldec.getObject(),
            associationWaldec.getSocialObject1(),
            associationWaldec.getSocialObject2(),
            associationWaldec.getAddressComplement(),
            associationWaldec.getAddressStreetNumber(),
            associationWaldec.getAddressRepetition(),
            associationWaldec.getAddressStreetType(),
            associationWaldec.getAddressStreetLibelle(),
            associationWaldec.getAddressDistribution(),
            associationWaldec.getAddressPostalCode(),
            associationWaldec.getAddressInseeCode(),
            associationWaldec.getAddressCityLibelle(),
            associationWaldec.getGestionDeclarantSurname(),
            associationWaldec.getGestionAddressComplementId(),
            associationWaldec.getGestionAddressComplementGeo(),
            associationWaldec.getGestionAddressStreetLibelle(),
            associationWaldec.getGestionAddressDistribution(),
            associationWaldec.getGestionAddressPostalCode(),
            associationWaldec.getGestionForward(),
            associationWaldec.getGestionCountry(),
            associationWaldec.getLeaderCivility(),
            associationWaldec.getWebsite(),
            associationWaldec.getPublicWebsite(),
            associationWaldec.getObservation(),
            associationWaldec.getPosition(),
            associationWaldec.getLastUpdated()
        );
    }
}
