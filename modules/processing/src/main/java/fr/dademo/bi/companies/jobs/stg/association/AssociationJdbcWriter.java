package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import fr.dademo.bi.companies.tools.batch.writer.JdbcRecordWriter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.AssociationTable.ASSOCIATION;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@ApplicationScoped
public class AssociationJdbcWriter implements JdbcRecordWriter<Association> {

    private static final Logger LOGGER = Logger.getLogger(AssociationJdbcWriter.class);

    @Inject
    @Named(STG_DSL_CONTEXT)
    @Getter
    DSLContext dslContext;

    @SneakyThrows
    @Override
    public void writeRecords(Batch<Association> batch) {

        LOGGER.info(String.format("Writing %d association documents", batch.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(ASSOCIATION,
                ASSOCIATION.FIELD_ASSOCIATION_ID,
                ASSOCIATION.FIELD_ASSOCIATION_ID_EX,
                ASSOCIATION.FIELD_ASSOCIATION_SIRET,
                ASSOCIATION.FIELD_ASSOCIATION_GESTION,
                ASSOCIATION.FIELD_ASSOCIATION_CREATION_DATE,
                ASSOCIATION.FIELD_ASSOCIATION_PUBLICATION_DATE,
                ASSOCIATION.FIELD_ASSOCIATION_NATURE,
                ASSOCIATION.FIELD_ASSOCIATION_GROUPEMENT,
                ASSOCIATION.FIELD_ASSOCIATION_TITLE,
                ASSOCIATION.FIELD_ASSOCIATION_OBJECT,
                ASSOCIATION.FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
                ASSOCIATION.FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_1,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_2,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_3,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
                ASSOCIATION.FIELD_ASSOCIATION_LEADER_CIVILITY,
                //ASSOCIATION.FIELD_ASSOCIATION_PHONE,
                ASSOCIATION.FIELD_ASSOCIATION_WEBSITE,
                //ASSOCIATION.FIELD_ASSOCIATION_EMAIL,
                ASSOCIATION.FIELD_ASSOCIATION_OBSERVATION,
                ASSOCIATION.FIELD_ASSOCIATION_POSITION,
                ASSOCIATION.FIELD_ASSOCIATION_RUP_CODE,
                ASSOCIATION.FIELD_ASSOCIATION_LAST_UPDATED
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null));

        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .map(this::companyBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyBind(Association association) {

        return batch -> batch.bind(
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
