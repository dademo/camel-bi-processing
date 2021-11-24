/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.datamodel;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class Company {

    public static final String CSV_FIELD_COMPANY_SIREN = "siren";
    public static final String CSV_FIELD_COMPANY_NIC = "nic";
    public static final String CSV_FIELD_COMPANY_SIRET = "siret";
    public static final String CSV_FIELD_COMPANY_COMPANY_DIFFUSION_STATUT = "statutDiffusionEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_CREATION_DATE = "dateCreationEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_RANGE = "trancheEffectifsEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_YEAR = "anneeEffectifsEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY = "activitePrincipaleRegistreMetiersEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_LAST_PROCESSING_DATE = "dateDernierTraitementEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_IS_HEADQUARTERS = "etablissementSiege";
    public static final String CSV_FIELD_COMPANY_COMPANY_PERIOD_COUNT = "nombrePeriodesEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT = "complementAdresseEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER = "numeroVoieEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION = "indiceRepetitionEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE = "typeVoieEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME = "libelleVoieEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE = "codePostalEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY = "libelleCommuneEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY = "libelleCommuneEtrangerEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION = "distributionSpecialeEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE = "codeCommuneEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE = "codeCedexEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME = "libelleCedexEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE = "codePaysEtrangerEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME = "libellePaysEtrangerEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT_2 = "complementAdresse2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_2 = "numeroVoie2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2 = "indiceRepetition2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE_2 = "typeVoie2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME_2 = "libelleVoie2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE_2 = "codePostal2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_2 = "libelleCommune2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY_2 = "libelleCommuneEtranger2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2 = "distributionSpeciale2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE_2 = "codeCommune2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE_2 = "codeCedex2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME_2 = "libelleCedex2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2 = "codePaysEtranger2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2 = "libellePaysEtranger2Etablissement";
    public static final String CSV_FIELD_COMPANY_BEGIN_DATE = "dateDebut";
    public static final String CSV_FIELD_COMPANY_COMPANY_ADMINISTATIVE_STATE = "etatAdministratifEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_NAME_1 = "enseigne1Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_NAME_2 = "enseigne2Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_NAME_3 = "enseigne3Etablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_USUAL_NAME = "denominationUsuelleEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_ACTIVITY = "activitePrincipaleEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_ACTIVITY_NAME = "nomenclatureActivitePrincipaleEtablissement";
    public static final String CSV_FIELD_COMPANY_COMPANY_IS_EMPLOYER = "caractereEmployeurEtablissement";
    public static final String[] CSV_HEADER_COMPANY = new String[]{ // NOSONAR
        CSV_FIELD_COMPANY_SIREN,
        CSV_FIELD_COMPANY_NIC,
        CSV_FIELD_COMPANY_SIRET,
        CSV_FIELD_COMPANY_COMPANY_DIFFUSION_STATUT,
        CSV_FIELD_COMPANY_COMPANY_CREATION_DATE,
        CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_RANGE,
        CSV_FIELD_COMPANY_COMPANY_STAFF_NUMBER_YEAR,
        CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY,
        CSV_FIELD_COMPANY_COMPANY_LAST_PROCESSING_DATE,
        CSV_FIELD_COMPANY_COMPANY_IS_HEADQUARTERS,
        CSV_FIELD_COMPANY_COMPANY_PERIOD_COUNT,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_COMPLEMENT_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_TYPE_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_STREET_NAME_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_POSTAL_CODE_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_2,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_CITY_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CITY_CODE_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_CODE_2,
        CSV_FIELD_COMPANY_COMPANY_ADDRESS_CEDEX_NAME_2,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2,
        CSV_FIELD_COMPANY_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2,
        CSV_FIELD_COMPANY_BEGIN_DATE,
        CSV_FIELD_COMPANY_COMPANY_ADMINISTATIVE_STATE,
        CSV_FIELD_COMPANY_COMPANY_NAME_1,
        CSV_FIELD_COMPANY_COMPANY_NAME_2,
        CSV_FIELD_COMPANY_COMPANY_NAME_3,
        CSV_FIELD_COMPANY_COMPANY_USUAL_NAME,
        CSV_FIELD_COMPANY_COMPANY_ACTIVITY,
        CSV_FIELD_COMPANY_COMPANY_PRINCIPAL_ACTIVITY_NAME,
        CSV_FIELD_COMPANY_COMPANY_IS_EMPLOYER,
    };

    @NotNull
    @NotBlank
    private String siren;

    @NotBlank
    private String nic;

    @NotBlank
    private String siret;

    private String companyDiffusionStatut;
    private LocalDate companyCreationDate;
    private String companyStaffNumberRange;
    private Integer companyStaffNumberYear;
    private String companyPrincipalRegisteredActivity;
    private LocalDateTime companyLastProcessingTimestamp;
    private Boolean companyIsHeadquarters;
    private Integer companyPeriodCount;
    private String companyAddressComplement;
    private String companyAddressStreetNumber;
    private String companyAddressStreetNumberRepetition;
    private String companyAddressStreetType;
    private String companyAddressStreetName;
    private String companyAddressPostalCode;
    private String companyAddressCity;
    private String companyForeignAddressCity;
    private String companyAddressSpecialDistribution;
    private String companyAddressCityCode;
    private String companyAddressCedexCode;
    private String companyAddressCedexName;
    private String companyForeignAddressCountryCode;
    private String companyForeignAddressCountryName;
    private String companyAddressComplement2;
    private String companyAddressStreetNumber2;
    private String companyAddressStreetNumberRepetition2;
    private String companyAddressStreetType2;
    private String companyAddressStreetName2;
    private String companyAddressPostalCode2;
    private String companyAddressCity2;
    private String companyForeignAddressCity2;
    private String companyAddressSpecialDistribution2;
    private String companyAddressCityCode2;
    private String companyAddressCedexCode2;
    private String companyAddressCedexName2;
    private String companyForeignAddressCountryCode2;
    private String companyForeignAddressCountryName2;
    private LocalDate beginDate;
    private String companyAdministativeState;
    private String companyName1;
    private String companyName2;
    private String companyName3;
    private String companyUsualName;
    private String companyActivity;
    private String companyPrincipalActivityName;
    private String companyIsEmployer;
}
