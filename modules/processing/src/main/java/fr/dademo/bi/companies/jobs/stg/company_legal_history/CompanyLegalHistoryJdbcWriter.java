package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
import fr.dademo.bi.companies.tools.batch.writer.BatchWriterTools;
import fr.dademo.bi.companies.tools.batch.writer.JdbcRecordWriter;
import lombok.Getter;
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

import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.COMPANY_LEGAL_HISTORY;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@ApplicationScoped
public class CompanyLegalHistoryJdbcWriter implements JdbcRecordWriter<CompanyLegalHistory> {

    private static final Logger LOGGER = Logger.getLogger(CompanyLegalHistoryJdbcWriter.class);

    @Inject
    @Named(STG_DSL_CONTEXT)
    @Getter
    DSLContext dslContext;

    @Override
    public void writeRecords(Batch<CompanyLegalHistory> batch) {

        LOGGER.info(String.format("Writing %d company legal history documents", batch.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY_LEGAL_HISTORY,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_SIREN,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_END_DATE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER,
                COMPANY_LEGAL_HISTORY.FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE
        ).values((String) null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null
        ));

        BatchWriterTools.recordsStreamOfBatch(batch)
                .map(Record::getPayload)
                .map(this::companyHistoryBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> companyHistoryBind(CompanyLegalHistory companyLegalHistory) {

        return batch -> batch.bind(
                companyLegalHistory.getSiren(),
                companyLegalHistory.getEndDate(),
                companyLegalHistory.getBeginDate(),
                companyLegalHistory.getLegalUnitAdministrativeState(),
                companyLegalHistory.getLegalUnitAdministrativeStateChange(),
                companyLegalHistory.getLegalUnitLegalUnitName(),
                companyLegalHistory.getLegalUnitLegalUnitNameChange(),
                companyLegalHistory.getLegalUnitUsualName(),
                companyLegalHistory.getLegalUnitUsualNameChange(),
                companyLegalHistory.getLegalUnitDenomination(),
                companyLegalHistory.getLegalUnitDenominationChange(),
                companyLegalHistory.getLegalUnitUsualDenomination1(),
                companyLegalHistory.getLegalUnitUsualDenomination2(),
                companyLegalHistory.getLegalUnitUsualDenomination3(),
                companyLegalHistory.getLegalUnitUsualDenominationChange(),
                companyLegalHistory.getLegalUnitLegalCategory(),
                companyLegalHistory.getLegalUnitLegalCategoryChange(),
                companyLegalHistory.getLegalUnitPrincipalActivity(),
                companyLegalHistory.getLegalUnitPrincipalActivityNomenclature(),
                companyLegalHistory.getLegalUnitPrincipalActivityNomenclatureChange(),
                companyLegalHistory.getLegalUnitHeadquarterNic(),
                companyLegalHistory.getLegalUnitHeadquarterNicChange(),
                companyLegalHistory.getLegalUnitIsSocialAndSolidarityEconomy(),
                companyLegalHistory.getLegalUnitIsSocialAndSolidarityEconomyChange(),
                companyLegalHistory.getLegalUnitIsEmployer(),
                companyLegalHistory.getLegalUnitIsEmployerChange()
        );
    }
}
