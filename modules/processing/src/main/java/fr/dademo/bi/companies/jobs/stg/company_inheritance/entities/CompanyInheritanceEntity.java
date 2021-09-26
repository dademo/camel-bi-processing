package fr.dademo.bi.companies.jobs.stg.company_inheritance.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RegisterForReflection
@Entity
@Table(name = "company_inheritance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyInheritanceEntity {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_company_predecessor_siren", nullable = false)
    private String companyPredecessorSiren;

    @Column(name = "field_company_successor_siren", nullable = false)
    private String companySuccessorSiren;

    @Column(name = "field_company_succession_date", nullable = false)
    private LocalDate companySuccessionDate;

    @Column(name = "field_company_headquarter_change", nullable = false)
    private Boolean companyHeaderChanged;

    @Column(name = "field_company_economical_continuity", nullable = false)
    private Boolean companyEconomicalContinuity;

    @Column(name = "field_company_processing_date", nullable = false)
    private LocalDateTime companyProcessingTimestamp;
}
