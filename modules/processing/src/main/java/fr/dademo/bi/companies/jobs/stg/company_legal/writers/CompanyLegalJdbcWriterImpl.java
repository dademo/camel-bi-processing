package fr.dademo.bi.companies.jobs.stg.company_legal.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal.CompanyLegalWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegal;
import lombok.Getter;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_legal.JobDefinition.COMPANY_LEGAL_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal.datamodel.CompanyLegalTable.COMPANY_LEGAL;

@Component
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_JDBC_TYPE
)
public class CompanyLegalJdbcWriterImpl implements CompanyLegalWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyLegalJdbcWriterImpl.class);

    @Autowired
    @Qualifier(STG_DSL_CONTEXT)
    @Getter
    private DSLContext dslContext;

    @Override
    public void write(List<? extends CompanyLegal> items) {

        LOGGER.info("Writing {} company legal documents", items.size());

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY_LEGAL,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_SIREN,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_IS_PURGED,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_ACRONYM,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_SEX,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_BEGIN_DATE,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_NAME,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_DENOMINATION,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
                COMPANY_LEGAL.FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER
        ).values((String) null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null
        ));

        items.stream()
                .map(this::companyHistoryBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info("{} rows affected", totalUpdated);
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyHistoryBind(CompanyLegal companyLegal) {

        return items -> items.bind(
                companyLegal.getSiren(),
                companyLegal.getDiffusionStatus(),
                companyLegal.getIsPurged(),
                companyLegal.getCreationDate(),
                companyLegal.getAcronym(),
                companyLegal.getSex(),
                companyLegal.getFirstName1(),
                companyLegal.getFirstName2(),
                companyLegal.getFirstName3(),
                companyLegal.getFirstName4(),
                companyLegal.getUsualFirstName(),
                companyLegal.getPseudonym(),
                companyLegal.getAssociationIdentifier(),
                companyLegal.getStaffNumberRange(),
                companyLegal.getStaffNumberYear(),
                companyLegal.getLastProcessing(),
                companyLegal.getPeriodsCount(),
                companyLegal.getCompanyCategory(),
                companyLegal.getCompanyCategoryYear(),
                companyLegal.getBeginDate(),
                companyLegal.getAdministrativeState(),
                companyLegal.getName(),
                companyLegal.getUsualName(),
                companyLegal.getDenomination(),
                companyLegal.getUsualDenomination1(),
                companyLegal.getUsualDenomination2(),
                companyLegal.getUsualDenomination3(),
                companyLegal.getLegalCategory(),
                companyLegal.getPrincipalActivity(),
                companyLegal.getPrincipalActivityNomenclature(),
                companyLegal.getHeadquartersNic(),
                companyLegal.getIsSocialAndSolidarityEconomy(),
                companyLegal.getIsEmployer()
        );
    }
}
