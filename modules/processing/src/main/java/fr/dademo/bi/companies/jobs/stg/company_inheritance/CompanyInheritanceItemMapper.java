/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance.*;

/**
 * @author dademo
 */
@Component
public class CompanyInheritanceItemMapper implements ItemProcessor<CSVRecord, CompanyInheritance> {

    @Override
    public CompanyInheritance process(@Nonnull CSVRecord item) {
        return mappedToCompanyInheritance(item);
    }

    private CompanyInheritance mappedToCompanyInheritance(CSVRecord csvRecord) {

        return CompanyInheritance.builder()
            .companyPredecessorSiren(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_PREDECESSOR_SIREN))
            .companySuccessorSiren(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSOR_SIREN))
            .companySuccessionDate(toLocalDate(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSION_DATE)))
            .companyHeaderChanged(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_HEADQUARTER_CHANGE)))
            .companyEconomicalContinuity(toBoolean(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_ECONOMICAL_CONTINUITY)))
            .companyProcessingTimestamp(toLocalDateTime(csvRecord.get(CSV_FIELD_COMPANY_INHERITANCE_PROCESSING_DATE)))
            .build();
    }
}
