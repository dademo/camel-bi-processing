/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author dademo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInheritance {

    public static final String CSV_FIELD_COMPANY_INHERITANCE_PREDECESSOR_SIREN = "siretEtablissementPredecesseur";
    public static final String CSV_FIELD_COMPANY_INHERITANCE_SUCCESSOR_SIREN = "siretEtablissementSuccesseur";
    public static final String CSV_FIELD_COMPANY_INHERITANCE_SUCCESSION_DATE = "dateLienSuccession";
    public static final String CSV_FIELD_COMPANY_INHERITANCE_HEADQUARTER_CHANGE = "transfertSiege";
    public static final String CSV_FIELD_COMPANY_INHERITANCE_ECONOMICAL_CONTINUITY = "continuiteEconomique";
    public static final String CSV_FIELD_COMPANY_INHERITANCE_PROCESSING_DATE = "dateDernierTraitementLienSuccession";
    public static final String[] CSV_HEADER_COMPANY_INHERITANCE = new String[]{ // NOSONAR
        CSV_FIELD_COMPANY_INHERITANCE_PREDECESSOR_SIREN,
        CSV_FIELD_COMPANY_INHERITANCE_SUCCESSOR_SIREN,
        CSV_FIELD_COMPANY_INHERITANCE_SUCCESSION_DATE,
        CSV_FIELD_COMPANY_INHERITANCE_HEADQUARTER_CHANGE,
        CSV_FIELD_COMPANY_INHERITANCE_ECONOMICAL_CONTINUITY,
        CSV_FIELD_COMPANY_INHERITANCE_PROCESSING_DATE,
    };

    private String companyPredecessorSiren;
    private String companySuccessorSiren;
    private LocalDate companySuccessionDate;
    private Boolean companyHeaderChanged;
    private Boolean companyEconomicalContinuity;
    private LocalDateTime companyProcessingTimestamp;
}
