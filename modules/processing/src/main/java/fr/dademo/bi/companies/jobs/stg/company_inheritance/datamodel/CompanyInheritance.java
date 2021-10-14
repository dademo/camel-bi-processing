package fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInheritance {

    public static final String FIELD_COMPANY_PREDECESSOR_SIREN = "siretEtablissementPredecesseur";
    public static final String FIELD_COMPANY_SUCCESSOR_SIREN = "siretEtablissementSuccesseur";
    public static final String FIELD_COMPANY_SUCCESSION_DATE = "dateLienSuccession";
    public static final String FIELD_COMPANY_HEADQUARTER_CHANGE = "transfertSiege";
    public static final String FIELD_COMPANY_ECONOMICAL_CONTINUITY = "continuiteEconomique";
    public static final String FIELD_COMPANY_PROCESSING_DATE = "dateDernierTraitementLienSuccession";
    public static final String[] HEADER = new String[]{
            FIELD_COMPANY_PREDECESSOR_SIREN,
            FIELD_COMPANY_SUCCESSOR_SIREN,
            FIELD_COMPANY_SUCCESSION_DATE,
            FIELD_COMPANY_HEADQUARTER_CHANGE,
            FIELD_COMPANY_ECONOMICAL_CONTINUITY,
            FIELD_COMPANY_PROCESSING_DATE,
    };

    private String companyPredecessorSiren;
    private String companySuccessorSiren;
    private LocalDate companySuccessionDate;
    private Boolean companyHeaderChanged;
    private Boolean companyEconomicalContinuity;
    private LocalDateTime companyProcessingTimestamp;
}
