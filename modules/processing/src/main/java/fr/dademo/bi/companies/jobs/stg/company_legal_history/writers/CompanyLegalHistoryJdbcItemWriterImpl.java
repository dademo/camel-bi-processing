/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory;
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
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.JobDefinition.COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME;
import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistoryTable.COMPANY_LEGAL_HISTORY;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_LEGAL_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_JDBC_TYPE
)
public class CompanyLegalHistoryJdbcItemWriterImpl implements CompanyLegalHistoryItemWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyLegalHistoryJdbcItemWriterImpl.class);

    @Autowired
    @Qualifier(STG_DATASOURCE_DSL_CONTEXT_BEAN_NAME)
    @Getter
    private DSLContext dslContext;

    @Override
    public void write(List<? extends CompanyLegalHistory> items) {

        LOGGER.info("Writing {} company legal history documents", items.size());

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

    private Consumer<BatchBindStep> companyHistoryBind(CompanyLegalHistory companyLegalHistory) {

        return items -> items.bind(
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
