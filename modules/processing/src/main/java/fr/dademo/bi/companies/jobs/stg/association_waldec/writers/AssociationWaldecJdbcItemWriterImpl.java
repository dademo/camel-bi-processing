package fr.dademo.bi.companies.jobs.stg.association_waldec.writers;

import fr.dademo.bi.companies.jobs.stg.association_waldec.AssociationWaldecItemWriter;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldecTable.ASSOCIATION_WALDEC;

@Component
@ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
)
public class AssociationWaldecJdbcItemWriterImpl implements AssociationWaldecItemWriter {

    private static final Logger LOGGER = Logger.getLogger(AssociationWaldecJdbcItemWriterImpl.class);

    @Autowired
    @Qualifier(STG_DSL_CONTEXT)
    @Getter
    private DSLContext dslContext;

    @SneakyThrows
    @Override
    public void write(List<? extends AssociationWaldec> items) {

        LOGGER.info(String.format("Writing %d company documents", items.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(ASSOCIATION_WALDEC,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ID,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ID_EX,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_SIRET,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_RUP_CODE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_CREATION_DATE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_DECLARATION_DATE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_PUBLICATION_DATE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_DISSOLUTION_DATE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_NATURE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GROUPEMENT,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_TITLE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_TITLE_SHORT,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_OBJECT,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_COMPLEMENT,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_STREET_NUMBER,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_REPETITION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_STREET_TYPE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_STREET_LIBELLE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_DISTRIBUTION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_DECLARANT_SURNAME,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_ADDRESS_COMPLEMENT_ID,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_ADDRESS_COMPLEMENT_GEO,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_ADDRESS_STREET_LIBELLE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_ADDRESS_DISTRIBUTION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_ADDRESS_POSTAL_CODE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_FORWARD,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_GESTION_COUNTRY,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_LEADER_CIVILITY,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_PHONE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_WEBSITE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_EMAIL,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_PUBLIC_WEBSITE,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_OBSERVATION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_POSITION,
                ASSOCIATION_WALDEC.FIELD_ASSOCIATION_LAST_UPDATED
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null
        ));

        items.stream()
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

    private Consumer<BatchBindStep> companyBind(AssociationWaldec associationWaldec) {

        return items -> items.bind(
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
                associationWaldec.getPhone(),
                associationWaldec.getWebsite(),
                associationWaldec.getEmail(),
                associationWaldec.getPublicWebsite(),
                associationWaldec.getObservation()
        );
    }
}
