/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance.*;

/**
 * @author dademo
 */
@Component
public class CompanyInheritanceItemMapper implements ItemProcessor<WrappedRowResource, CompanyInheritance> {

    private CompanyInheritanceCsvColumnsMapping columnsIndexMapping;

    @Override
    public CompanyInheritance process(@Nonnull WrappedRowResource item) {
        return mappedToCompanyInheritance(item);
    }

    private CompanyInheritance mappedToCompanyInheritance(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return CompanyInheritance.builder()
            .companyPredecessorSiren(item.getString(columnsIndexMapping.getCompanyPredecessorSirenField()))
            .companySuccessorSiren(item.getString(columnsIndexMapping.getCompanySuccessorSirenField()))
            .companySuccessionDate(toLocalDate(item.getString(columnsIndexMapping.getCompanySuccessionDateField())))
            .companyHeaderChanged(toBoolean(item.getString(columnsIndexMapping.getCompanyHeaderChangedField())))
            .companyEconomicalContinuity(toBoolean(item.getString(columnsIndexMapping.getCompanyEconomicalContinuityField())))
            .companyProcessingTimestamp(toLocalDateTime(item.getString(columnsIndexMapping.getCompanyProcessingTimestampField())))
            .build();
    }

    private CompanyInheritanceCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return CompanyInheritanceCsvColumnsMapping.builder()
            .companyPredecessorSirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_PREDECESSOR_SIREN))
            .companySuccessorSirenField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSOR_SIREN))
            .companySuccessionDateField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_SUCCESSION_DATE))
            .companyHeaderChangedField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_HEADQUARTER_CHANGE))
            .companyEconomicalContinuityField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_ECONOMICAL_CONTINUITY))
            .companyProcessingTimestampField(item.getColumnIndexByName(CSV_FIELD_COMPANY_INHERITANCE_PROCESSING_DATE))
            .build();
    }
}
