package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.datamodel.Company;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.record.Batch;
import org.jeasy.batch.core.record.Record;
import org.jeasy.batch.core.writer.RecordWriter;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.company.datamodel.CompanyTable.COMPANY;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@ApplicationScoped
public class CompanyWriter implements RecordWriter<Company> {

    private static final Logger LOGGER = Logger.getLogger(CompanyWriter.class);

    @Inject
    @Named(STG_DSL_CONTEXT)
    DSLContext dslContext;

    @SneakyThrows
    @Override
    public void writeRecords(Batch<Company> batch) {

        LOGGER.info(String.format("Writing %d naf definition documents", batch.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY,
                COMPANY.FIELD_SIREN,
                COMPANY.FIELD_NIC,
                COMPANY.FIELD_SIRET,
                COMPANY.FIELD_COMPANY_DIFFUSION_STATUT,
                COMPANY.FIELD_COMPANY_CREATION_DATE,
                COMPANY.FIELD_COMPANY_STAFF_NUMBER_RANGE,
                COMPANY.FIELD_COMPANY_STAFF_NUMBER_YEAR,
                COMPANY.FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY,
                COMPANY.FIELD_COMPANY_LAST_PROCESSING,
                COMPANY.FIELD_COMPANY_IS_HEADQUARTERS,
                COMPANY.FIELD_COMPANY_PERIOD_COUNT,
                COMPANY.FIELD_COMPANY_ADDRESS_COMPLEMENT,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_TYPE,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NAME,
                COMPANY.FIELD_COMPANY_ADDRESS_POSTAL_CODE,
                COMPANY.FIELD_COMPANY_ADDRESS_CITY,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_CITY,
                COMPANY.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION,
                COMPANY.FIELD_COMPANY_ADDRESS_CITY_CODE,
                COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_CODE,
                COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_NAME,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME,
                COMPANY.FIELD_COMPANY_ADDRESS_COMPLEMENT_2,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_2,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_TYPE_2,
                COMPANY.FIELD_COMPANY_ADDRESS_STREET_NAME_2,
                COMPANY.FIELD_COMPANY_ADDRESS_POSTAL_CODE_2,
                COMPANY.FIELD_COMPANY_ADDRESS_CITY_2,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2,
                COMPANY.FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2,
                COMPANY.FIELD_COMPANY_ADDRESS_CITY_CODE_2,
                COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_CODE_2,
                COMPANY.FIELD_COMPANY_ADDRESS_CEDEX_NAME_2,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2,
                COMPANY.FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2,
                COMPANY.FIELD_BEGIN_DATE,
                COMPANY.FIELD_COMPANY_ADMINISTATIVE_STATE,
                COMPANY.FIELD_COMPANY_NAME_1,
                COMPANY.FIELD_COMPANY_NAME_2,
                COMPANY.FIELD_COMPANY_NAME_3,
                COMPANY.FIELD_COMPANY_USUAL_NAME,
                COMPANY.FIELD_COMPANY_ACTIVITY,
                COMPANY.FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME,
                COMPANY.FIELD_COMPANY_IS_EMPLOYER
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null
        ));

        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .map(this::companyBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final var totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyBind(Company company) {

        return batch -> batch.bind(
                company.getSiren(),
                company.getNic(),
                company.getSiret(),
                company.getCompanyDiffusionStatut(),
                company.getCompanyCreationDate(),
                company.getCompanyStaffNumberRange(),
                company.getCompanyStaffNumberYear(),
                company.getCompanyPrincipalRegisteredActivity(),
                company.getCompanyLastProcessingTimestamp(),
                company.getCompanyIsHeadquarters(),
                company.getCompanyPeriodCount(),
                company.getCompanyAddressComplement(),
                company.getCompanyAddressStreetNumber(),
                company.getCompanyAddressStreetNumberRepetition(),
                company.getCompanyAddressStreetType(),
                company.getCompanyAddressStreetName(),
                company.getCompanyAddressPostalCode(),
                company.getCompanyAddressCity(),
                company.getCompanyForeignAddressCity(),
                company.getCompanyAddressSpecialDistribution(),
                company.getCompanyAddressCityCode(),
                company.getCompanyAddressCedexCode(),
                company.getCompanyAddressCedexName(),
                company.getCompanyForeignAddressCountryCode(),
                company.getCompanyForeignAddressCountryName(),
                company.getCompanyAddressComplement2(),
                company.getCompanyAddressStreetNumberRepetition2(),
                company.getCompanyAddressStreetNumberRepetition2(),
                company.getCompanyAddressStreetType2(),
                company.getCompanyAddressStreetName2(),
                company.getCompanyAddressPostalCode2(),
                company.getCompanyAddressCity2(),
                company.getCompanyForeignAddressCity2(),
                company.getCompanyAddressSpecialDistribution2(),
                company.getCompanyAddressCityCode2(),
                company.getCompanyAddressCedexCode2(),
                company.getCompanyAddressCedexName2(),
                company.getCompanyForeignAddressCountryCode2(),
                company.getCompanyForeignAddressCountryName2(),
                company.getBeginDate(),
                company.getCompanyAdministativeState(),
                company.getCompanyName1(),
                company.getCompanyName2(),
                company.getCompanyName3(),
                company.getCompanyUsualName(),
                company.getCompanyActivity(),
                company.getCompanyPrincipalActivityName(),
                company.getCompanyIsEmployer()
        );
    }
}
