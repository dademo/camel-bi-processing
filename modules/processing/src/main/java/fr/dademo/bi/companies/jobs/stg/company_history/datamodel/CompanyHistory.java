/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history.datamodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * @author dademo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyHistory {

    public static final String CSV_FIELD_COMPANY_HISTORY_SIREN = "siren";
    public static final String CSV_FIELD_COMPANY_HISTORY_NIC = "nic";
    public static final String CSV_FIELD_COMPANY_HISTORY_SIRET = "siret";
    public static final String CSV_FIELD_COMPANY_HISTORY_END_DATE = "end_date";
    public static final String CSV_FIELD_COMPANY_HISTORY_BEGIN_DATE = "begin_date";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE = "institution_administrative_state";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE = "institution_administrative_state_change";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_1_NAME = "institution_1_name";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_2_NAME = "institution_2_name";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_3_NAME = "institution_3_name";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_NAME_CHANGE = "institution_name_change";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME = "institution_usual_name";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME_CHANGE = "institution_usual_name_change";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY = "institution_primary_activity";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE = "institution_primary_activity_nomenclature";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_CHANGE = "institution_primary_activity_change";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE = "institution_employer_nature";
    public static final String CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE_CHANGE = "institution_employer_nature_change";
    public static final String[] CSV_HEADER_COMPANY_HISTORY = new String[]{ // NOSONAR
        CSV_FIELD_COMPANY_HISTORY_SIREN,
        CSV_FIELD_COMPANY_HISTORY_NIC,
        CSV_FIELD_COMPANY_HISTORY_SIRET,
        CSV_FIELD_COMPANY_HISTORY_END_DATE,
        CSV_FIELD_COMPANY_HISTORY_BEGIN_DATE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_1_NAME,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_2_NAME,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_3_NAME,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_NAME_CHANGE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_USUAL_NAME_CHANGE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_PRIMARY_ACTIVITY_CHANGE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE,
        CSV_FIELD_COMPANY_HISTORY_INSTITUTION_EMPLOYER_NATURE_CHANGE,
    };

    @NotNull
    @NotBlank
    private String siren;

    @NotNull
    @NotBlank
    private String nic;

    @NotNull
    @NotBlank
    private String siret;

    private LocalDate endDate;
    private LocalDate beginDate;
    private String institutionAdministrativeState;
    private Boolean institutionAdministrativeStateChange;
    private String institution1Name;
    private String institution2Name;
    private String institution3Name;
    private Boolean institutionNameChange;
    private String institutionUsualName;
    private Boolean institutionUsualNameChange;
    private String institutionPrimaryActivity;
    private String institutionPrimaryActivityNomenclature;
    private Boolean institutionPrimaryActivityChange;
    private String institutionEmployerNature;
    private Boolean institutionEmployerNatureChange;

    @AllArgsConstructor
    @Getter
    @Builder
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class CompanyHistoryCsvColumnsMapping {

        int sirenField;
        int nicField;
        int siretField;
        int endDateField;
        int beginDateField;
        int institutionAdministrativeStateField;
        int institutionAdministrativeStateChangeField;
        int institution1NameField;
        int institution2NameField;
        int institution3NameField;
        int institutionNameChangeField;
        int institutionUsualNameField;
        int institutionUsualNameChangeField;
        int institutionPrimaryActivityField;
        int institutionPrimaryActivityNomenclatureField;
        int institutionPrimaryActivityChangeField;
        int institutionEmployerNatureField;
        int institutionEmployerNatureChangeField;
    }
}
