package fr.dademo.bi.companies.jobs.stg.company_history;

import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import lombok.Getter;
import org.jboss.logging.Logger;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistoryTable.COMPANY_HISTORY;
import static fr.dademo.bi.companies.tools.DefaultAppBeans.STG_DSL_CONTEXT;

@Component
public class CompanyHistoryJdbcWriter implements ItemWriter<CompanyHistory> {

    private static final Logger LOGGER = Logger.getLogger(CompanyHistoryJdbcWriter.class);

    @Autowired
    @Qualifier(STG_DSL_CONTEXT)
    @Getter
    private DSLContext dslContext;

    @Override
    public void write(List<? extends CompanyHistory> items) {

        LOGGER.info(String.format("Writing %d company history documents", items.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(COMPANY_HISTORY,
                COMPANY_HISTORY.FIELD_SIREN,
                COMPANY_HISTORY.FIELD_NIC,
                COMPANY_HISTORY.FIELD_SIRET,
                COMPANY_HISTORY.FIELD_END_DATE,
                COMPANY_HISTORY.FIELD_BEGIN_DATE,
                COMPANY_HISTORY.FIELD_INSTITUTION_ADMINISTRATIVE_STATE,
                COMPANY_HISTORY.FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE,
                COMPANY_HISTORY.FIELD_INSTITUTION_1_NAME,
                COMPANY_HISTORY.FIELD_INSTITUTION_2_NAME,
                COMPANY_HISTORY.FIELD_INSTITUTION_3_NAME,
                COMPANY_HISTORY.FIELD_INSTITUTION_NAME_CHANGE,
                COMPANY_HISTORY.FIELD_INSTITUTION_USUAL_NAME,
                COMPANY_HISTORY.FIELD_INSTITUTION_USUAL_NAME_CHANGE,
                COMPANY_HISTORY.FIELD_INSTITUTION_PRIMARY_ACTIVITY,
                COMPANY_HISTORY.FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE,
                COMPANY_HISTORY.FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE,
                COMPANY_HISTORY.FIELD_INSTITUTION_EMPLOYER_NATURE
        ).values((String) null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null
        ));

        items.stream()
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

    private Consumer<BatchBindStep> companyHistoryBind(CompanyHistory companyHistory) {

        return items -> items.bind(
                companyHistory.getSiren(),
                companyHistory.getNic(),
                companyHistory.getSiret(),
                companyHistory.getEndDate(),
                companyHistory.getBeginDate(),
                companyHistory.getInstitutionAdministrativeState(),
                companyHistory.getInstitutionAdministrativeStateChange(),
                companyHistory.getInstitution1Name(),
                companyHistory.getInstitution2Name(),
                companyHistory.getInstitution3Name(),
                companyHistory.getInstitutionNameChange(),
                companyHistory.getInstitutionUsualName(),
                companyHistory.getInstitutionUsualNameChange(),
                companyHistory.getInstitutionPrimaryActivity(),
                companyHistory.getInstitutionPrimaryActivityNomenclature(),
                companyHistory.getInstitutionPrimaryActivityChange(),
                companyHistory.getInstitutionEmployerNature(),
                companyHistory.getInstitutionEmployerNatureChange()
        );
    }
}
