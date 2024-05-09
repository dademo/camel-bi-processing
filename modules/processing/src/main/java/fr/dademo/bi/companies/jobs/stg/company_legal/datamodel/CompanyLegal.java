/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal.datamodel;

import lombok.*;
import lombok.experimental.FieldDefaults;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CompanyLegal {

    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_SIREN = "siren";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS = "statutDiffusionUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_IS_PURGED = "unitePurgeeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE = "dateCreationUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_ACRONYM = "sigleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_SEX = "sexeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1 = "prenom1UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2 = "prenom2UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3 = "prenom3UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4 = "prenom4UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME = "prenomUsuelUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM = "pseudonymeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER = "identifiantAssociationUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE = "trancheEffectifsUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR = "anneeEffectifsUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING = "dateDernierTraitementUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT = "nombrePeriodesUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY = "categorieEntreprise";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR = "anneeCategorieEntreprise";
    public static final String CSV_FIELD_COMPANY_LEGAL_BEGIN_DATE = "dateDebut";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE = "etatAdministratifUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_NAME = "nomUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME = "nomUsageUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_DENOMINATION = "denominationUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1 = "denominationUsuelle1UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2 = "denominationUsuelle2UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3 = "denominationUsuelle3UniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY = "categorieJuridiqueUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY = "activitePrincipaleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE = "nomenclatureActivitePrincipaleUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC = "nicSiegeUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY = "economieSocialeSolidaireUniteLegale";
    public static final String CSV_FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER = "caractereEmployeurUniteLegale";
    public static final String[] CSV_HEADER_COMPANY_LEGAL = new String[]{ // NOSONAR
        CSV_FIELD_COMPANY_LEGAL_UNIT_SIREN,
        CSV_FIELD_COMPANY_LEGAL_UNIT_DIFFUSION_STATUS,
        CSV_FIELD_COMPANY_LEGAL_UNIT_IS_PURGED,
        CSV_FIELD_COMPANY_LEGAL_UNIT_CREATION_DATE,
        CSV_FIELD_COMPANY_LEGAL_UNIT_ACRONYM,
        CSV_FIELD_COMPANY_LEGAL_UNIT_SEX,
        CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_1,
        CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_2,
        CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_3,
        CSV_FIELD_COMPANY_LEGAL_UNIT_FIRST_NAME_4,
        CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_FIRST_NAME,
        CSV_FIELD_COMPANY_LEGAL_UNIT_PSEUDONYM,
        CSV_FIELD_COMPANY_LEGAL_UNIT_ASSOCIATION_IDENTIFIER,
        CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_RANGE,
        CSV_FIELD_COMPANY_LEGAL_UNIT_STAFF_NUMBER_YEAR,
        CSV_FIELD_COMPANY_LEGAL_UNIT_LAST_PROCESSING,
        CSV_FIELD_COMPANY_LEGAL_UNIT_PERIODS_COUNT,
        CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY,
        CSV_FIELD_COMPANY_LEGAL_UNIT_COMPANY_CATEGORY_YEAR,
        CSV_FIELD_COMPANY_LEGAL_BEGIN_DATE,
        CSV_FIELD_COMPANY_LEGAL_UNIT_ADMINISTRATIVE_STATE,
        CSV_FIELD_COMPANY_LEGAL_UNIT_NAME,
        CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_NAME,
        CSV_FIELD_COMPANY_LEGAL_UNIT_DENOMINATION,
        CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_1,
        CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_2,
        CSV_FIELD_COMPANY_LEGAL_UNIT_USUAL_DENOMINATION_3,
        CSV_FIELD_COMPANY_LEGAL_UNIT_LEGAL_CATEGORY,
        CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY,
        CSV_FIELD_COMPANY_LEGAL_UNIT_PRINCIPAL_ACTIVITY_NOMENCLATURE,
        CSV_FIELD_COMPANY_LEGAL_UNIT_HEADQUARTERS_NIC,
        CSV_FIELD_COMPANY_LEGAL_UNIT_IS_SOCIAL_AND_SOLIDARITY_ECONOMY,
        CSV_FIELD_COMPANY_LEGAL_UNIT_IS_EMPLOYER,
    };

    @NotNull
    @NotBlank
    private String siren;

    private String diffusionStatus;
    private Boolean isPurged;
    private LocalDate creationDate;
    private String acronym;
    private String sex;
    private String firstName1;
    private String firstName2;
    private String firstName3;
    private String firstName4;
    private String usualFirstName;
    private String pseudonym;
    private String associationIdentifier;
    private String staffNumberRange;
    private Integer staffNumberYear;
    private LocalDateTime lastProcessing;
    private Integer periodsCount;
    private String companyCategory;
    private Integer companyCategoryYear;
    private LocalDate beginDate;
    private String administrativeState;
    private String name;
    private String usualName;
    private String denomination;
    private String usualDenomination1;
    private String usualDenomination2;
    private String usualDenomination3;
    private String legalCategory;
    private String principalActivity;
    private String principalActivityNomenclature;
    private String headquartersNic;
    private String isSocialAndSolidarityEconomy;
    private String isEmployer;

    @AllArgsConstructor
    @Getter
    @Builder
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class CompanyLegalCsvColumnsMapping {

        int sirenField;
        int diffusionStatusField;
        int isPurgedField;
        int creationDateField;
        int acronymField;
        int sexField;
        int firstName1Field;
        int firstName2Field;
        int firstName3Field;
        int firstName4Field;
        int usualFirstNameField;
        int pseudonymField;
        int associationIdentifierField;
        int staffNumberRangeField;
        int staffNumberYearField;
        int lastProcessingField;
        int periodsCountField;
        int companyCategoryField;
        int companyCategoryYearField;
        int beginDateField;
        int administrativeStateField;
        int nameField;
        int usualNameField;
        int denominationField;
        int usualDenomination1Field;
        int usualDenomination2Field;
        int usualDenomination3Field;
        int legalCategoryField;
        int principalActivityField;
        int principalActivityNomenclatureField;
        int headquartersNicField;
        int isSocialAndSolidarityEconomyField;
        int isEmployerField;
    }
}
