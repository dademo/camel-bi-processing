/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author dademo
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLegalHistory {

    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_SIREN = "siren";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_END_DATE = "dateFin";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE = "dateDebut";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE = "etatAdministratifUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE = "changementEtatAdministratifUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME = "nomUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE = "changementNomUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME = "nomUsageUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE = "changementNomUsageUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION = "denominationUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE = "changementDenominationUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1 = "denominationUsuelle1UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2 = "denominationUsuelle2UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3 = "denominationUsuelle3UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE = "changementDenominationUsuelleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY = "categorieJuridiqueUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE = "changementCategorieJuridiqueUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY = "activitePrincipaleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE = "nomenclatureActivitePrincipaleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE = "changementActivitePrincipaleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC = "nicSiegeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE = "changementNicSiegeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY = "economieSocialeSolidaireUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE = "changementEconomieSocialeSolidaireUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER = "caractereEmployeurUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE = "changementCaractereEmployeurUniteLegale";
    public static final String[] CSV_HEADER_COMPANY_LEGAL_HISTORY = new String[]{ // NOSONAR
        CSV_FIELD_COMPANY_LEGAL_HISTORY_SIREN,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_END_DATE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_BEGIN_DATE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_ADMINISTRATIVE_STATE_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_UNIT_NAME_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_NAME_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_DENOMINATION_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_1,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_2,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_3,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_USUAL_DENOMINATION_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_LEGAL_CATEGORY_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_HEADQUARTER_NIC_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY_CHANGE,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER,
        CSV_FIELD_COMPANY_LEGAL_HISTORY_LEGAL_UNIT_IS_EMPLOYER_CHANGE,
    };

    @NotNull
    @NotBlank
    private String siren;

    private LocalDate endDate;
    private LocalDate beginDate;
    private String legalUnitAdministrativeState;
    private Boolean legalUnitAdministrativeStateChange;
    private String legalUnitLegalUnitName;
    private Boolean legalUnitLegalUnitNameChange;
    private String legalUnitUsualName;
    private Boolean legalUnitUsualNameChange;
    private String legalUnitDenomination;
    private Boolean legalUnitDenominationChange;
    private String legalUnitUsualDenomination1;
    private String legalUnitUsualDenomination2;
    private String legalUnitUsualDenomination3;
    private Boolean legalUnitUsualDenominationChange;
    private String legalUnitLegalCategory;
    private Boolean legalUnitLegalCategoryChange;
    private String legalUnitPrincipalActivity;
    private String legalUnitPrincipalActivityNomenclature;
    private Boolean legalUnitPrincipalActivityNomenclatureChange;
    private String legalUnitHeadquarterNic;
    private Boolean legalUnitHeadquarterNicChange;
    private String legalUnitIsSocialAndSolidarityEconomy;
    private Boolean legalUnitIsSocialAndSolidarityEconomyChange;
    private String legalUnitIsEmployer;
    private Boolean legalUnitIsEmployerChange;

    @AllArgsConstructor
    @Getter
    @Builder
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class CompanyLegalHistoryCsvColumnsMapping {

        int sirenField;
        int endDateField;
        int beginDateField;
        int legalUnitAdministrativeStateField;
        int legalUnitAdministrativeStateChangeField;
        int legalUnitLegalUnitNameField;
        int legalUnitLegalUnitNameChangeField;
        int legalUnitUsualNameField;
        int legalUnitUsualNameChangeField;
        int legalUnitDenominationField;
        int legalUnitDenominationChangeField;
        int legalUnitUsualDenomination1Field;
        int legalUnitUsualDenomination2Field;
        int legalUnitUsualDenomination3Field;
        int legalUnitUsualDenominationChangeField;
        int legalUnitLegalCategoryField;
        int legalUnitLegalCategoryChangeField;
        int legalUnitPrincipalActivityField;
        int legalUnitPrincipalActivityNomenclatureField;
        int legalUnitPrincipalActivityNomenclatureChangeField;
        int legalUnitHeadquarterNicField;
        int legalUnitHeadquarterNicChangeField;
        int legalUnitIsSocialAndSolidarityEconomyField;
        int legalUnitIsSocialAndSolidarityEconomyChangeField;
        int legalUnitIsEmployerField;
        int legalUnitIsEmployerChangeField;
    }
}
