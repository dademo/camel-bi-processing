package fr.dademo.bi.companies.process.stg.companies_history.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RegisterForReflection
@CsvRecord(separator = ",")
@Entity
@Table(name = "fact_historical_companies")
@Getter
@Setter
@NoArgsConstructor
public class CompanyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "siren", length = 9, nullable = false)
    @DataField(pos = 1, columnName = "siren", required = true)
    private String siren;

    @NotNull
    @NotBlank
    @Column(name = "nic", length = 5, nullable = false)
    @DataField(pos = 2, columnName = "nic", required = true)
    private String nic;

    @NotNull
    @NotBlank
    @Column(name = "siret", length = 14, nullable = false)
    @DataField(pos = 3, columnName = "siret", required = true)
    private String siret;

    @Column(name = "end_date", length = 14)
    @DataField(pos = 4, columnName = "dateFin", pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "begin_date", length = 14)
    @DataField(pos = 5, columnName = "dateDebut", pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @Column(name = "institution_administrative_state", length = 1)
    @DataField(pos = 6, columnName = "etatAdministratifEtablissement")
    private String institutionAdministrativeState;

    @Column(name = "institution_administrative_state_change")
    @DataField(pos = 7, columnName = "changementEtatAdministratifEtablissement")
    private Boolean institutionAdministrativeStateChange;

    @Column(name = "institution_1_name")
    @DataField(pos = 8, columnName = "enseigne1Etablissement")
    private String institution1Name;

    @Column(name = "institution_2_name")
    @DataField(pos = 9, columnName = "enseigne2Etablissement")
    private String institution2Name;

    @Column(name = "institution_3_name")
    @DataField(pos = 10, columnName = "enseigne3Etablissement")
    private String institution3Name;

    @Column(name = "institution_name_change")
    @DataField(pos = 11, columnName = "changementEnseigneEtablissement")
    private Boolean institutionNameChange;

    @Column(name = "institution_usual_name", length = Integer.MAX_VALUE)
    @DataField(pos = 12, columnName = "denominationUsuelleEtablissement")
    private String institutionUsualName;

    @Column(name = "institution_usual_name_change")
    @DataField(pos = 13, columnName = "changementDenominationUsuelleEtablissement")
    private Boolean institutionUsualNameChange;

    @Column(name = "institution_primary_activity", length = 6)
    @DataField(pos = 14, columnName = "activitePrincipaleEtablissement")
    private String institutionPrimaryActivity;

    @Column(name = "institution_primary_activity_nomenclature", length = 8)
    @DataField(pos = 15, columnName = "nomenclatureActivitePrincipaleEtablissement")
    private String institutionPrimaryActivityNomenclature;

    @Column(name = "institution_primary_activity_change")
    @DataField(pos = 16, columnName = "changementActivitePrincipaleEtablissement")
    private Boolean institutionPrimaryActivityChange;

    @Column(name = "institution_employer_nature", length = 1)
    @DataField(pos = 17, columnName = "caractereEmployeurEtablissement")
    private String institutionEmployerNature;

    @Column(name = "institution_employer_nature_change")
    @DataField(pos = 18, columnName = "changementCaractereEmployeurEtablissement")
    private Boolean institutionEmployerNatureChange;
}
