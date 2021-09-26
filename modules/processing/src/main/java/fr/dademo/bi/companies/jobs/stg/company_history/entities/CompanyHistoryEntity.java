package fr.dademo.bi.companies.jobs.stg.company_history.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RegisterForReflection
@Entity
@Table(name = "company_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyHistoryEntity {

    public static final String FIELD_SIREN = "siren";
    public static final String FIELD_NIC = "nic";
    public static final String FIELD_SIRET = "siret";
    public static final String FIELD_END_DATE = "end_date";
    public static final String FIELD_BEGIN_DATE = "begin_date";
    public static final String FIELD_INSTITUTION_ADMINISTRATIVE_STATE = "institution_administrative_state";
    public static final String FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE = "institution_administrative_state_change";
    public static final String FIELD_INSTITUTION_1_NAME = "institution_1_name";
    public static final String FIELD_INSTITUTION_2_NAME = "institution_2_name";
    public static final String FIELD_INSTITUTION_3_NAME = "institution_3_name";
    public static final String FIELD_INSTITUTION_NAME_CHANGE = "institution_name_change";
    public static final String FIELD_INSTITUTION_USUAL_NAME = "institution_usual_name";
    public static final String FIELD_INSTITUTION_USUAL_NAME_CHANGE = "institution_usual_name_change";
    public static final String FIELD_INSTITUTION_PRIMARY_ACTIVITY = "institution_primary_activity";
    public static final String FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE = "institution_primary_activity_nomenclature";
    public static final String FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE = "institution_primary_activity_change";
    public static final String FIELD_INSTITUTION_EMPLOYER_NATURE = "institution_employer_nature";
    public static final String FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE = "institution_employer_nature_change";
    public static final String[] HEADER = new String[]{
            FIELD_SIREN,
            FIELD_NIC,
            FIELD_SIRET,
            FIELD_END_DATE,
            FIELD_BEGIN_DATE,
            FIELD_INSTITUTION_ADMINISTRATIVE_STATE,
            FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE,
            FIELD_INSTITUTION_1_NAME,
            FIELD_INSTITUTION_2_NAME,
            FIELD_INSTITUTION_3_NAME,
            FIELD_INSTITUTION_NAME_CHANGE,
            FIELD_INSTITUTION_USUAL_NAME,
            FIELD_INSTITUTION_USUAL_NAME_CHANGE,
            FIELD_INSTITUTION_PRIMARY_ACTIVITY,
            FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE,
            FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE,
            FIELD_INSTITUTION_EMPLOYER_NATURE,
            FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE,
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "siren", length = 9, nullable = false)
    private String siren;

    @NotNull
    @NotBlank
    @Column(name = "nic", length = 5, nullable = false)
    private String nic;

    @NotNull
    @NotBlank
    @Column(name = "siret", length = 14, nullable = false)
    private String siret;

    @Column(name = "end_date", length = 14)
    private LocalDate endDate;

    @Column(name = "begin_date", length = 14)
    private LocalDate beginDate;

    @Column(name = "institution_administrative_state", length = 1)
    private String institutionAdministrativeState;

    @Column(name = "institution_administrative_state_change")
    private Boolean institutionAdministrativeStateChange;

    @Column(name = "institution_1_name")
    private String institution1Name;

    @Column(name = "institution_2_name")
    private String institution2Name;

    @Column(name = "institution_3_name")
    private String institution3Name;

    @Column(name = "institution_name_change")
    private Boolean institutionNameChange;

    @Column(name = "institution_usual_name", length = Integer.MAX_VALUE)
    private String institutionUsualName;

    @Column(name = "institution_usual_name_change")
    private Boolean institutionUsualNameChange;

    @Column(name = "institution_primary_activity", length = 6)
    private String institutionPrimaryActivity;

    @Column(name = "institution_primary_activity_nomenclature", length = 8)
    private String institutionPrimaryActivityNomenclature;

    @Column(name = "institution_primary_activity_change")
    private Boolean institutionPrimaryActivityChange;

    @Column(name = "institution_employer_nature", length = 1)
    private String institutionEmployerNature;

    @Column(name = "institution_employer_nature_change")
    private Boolean institutionEmployerNatureChange;
}
