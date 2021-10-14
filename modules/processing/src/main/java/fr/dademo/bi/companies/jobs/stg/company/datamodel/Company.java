package fr.dademo.bi.companies.jobs.stg.company.datamodel;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    public static final String FIELD_SIREN = "siren";
    public static final String FIELD_NIC = "nic";
    public static final String FIELD_SIRET = "siret";
    public static final String FIELD_COMPANY_DIFFUSION_STATUT = "statutDiffusionEtablissement";
    public static final String FIELD_COMPANY_CREATION_DATE = "dateCreationEtablissement";
    public static final String FIELD_COMPANY_STAFF_NUMBER_RANGE = "trancheEffectifsEtablissement";
    public static final String FIELD_COMPANY_STAFF_NUMBER_YEAR = "anneeEffectifsEtablissement";
    public static final String FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY = "activitePrincipaleRegistreMetiersEtablissement";
    public static final String FIELD_COMPANY_LAST_PROCESSING = "dateDernierTraitementEtablissement";
    public static final String FIELD_COMPANY_IS_HEADQUARTERS = "etablissementSiege";
    public static final String FIELD_COMPANY_PERIOD_COUNT = "nombrePeriodesEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_COMPLEMENT = "complementAdresseEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NUMBER = "numeroVoieEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION = "indiceRepetitionEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_TYPE = "typeVoieEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NAME = "libelleVoieEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_POSTAL_CODE = "codePostalEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_CITY = "libelleCommuneEtablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_CITY = "libelleCommuneEtrangerEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION = "distributionSpecialeEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_CITY_CODE = "codeCommuneEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_CEDEX_CODE = "codeCedexEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_CEDEX_NAME = "libelleCedexEtablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE = "codePaysEtrangerEtablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME = "libellePaysEtrangerEtablissement";
    public static final String FIELD_COMPANY_ADDRESS_COMPLEMENT_2 = "complementAdresse2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NUMBER_2 = "numeroVoie2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2 = "indiceRepetition2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_TYPE_2 = "typeVoie2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_STREET_NAME_2 = "libelleVoie2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_POSTAL_CODE_2 = "codePostal2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_CITY_2 = "libelleCommune2Etablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2 = "libelleCommuneEtranger2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2 = "distributionSpeciale2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_CITY_CODE_2 = "codeCommune2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_CEDEX_CODE_2 = "codeCedex2Etablissement";
    public static final String FIELD_COMPANY_ADDRESS_CEDEX_NAME_2 = "libelleCedex2Etablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2 = "codePaysEtranger2Etablissement";
    public static final String FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2 = "libellePaysEtranger2Etablissement";
    public static final String FIELD_BEGIN_DATE = "dateDebut";
    public static final String FIELD_COMPANY_ADMINISTATIVE_STATE = "etatAdministratifEtablissement";
    public static final String FIELD_COMPANY_NAME_1 = "enseigne1Etablissement";
    public static final String FIELD_COMPANY_NAME_2 = "enseigne2Etablissement";
    public static final String FIELD_COMPANY_NAME_3 = "enseigne3Etablissement";
    public static final String FIELD_COMPANY_USUAL_NAME = "denominationUsuelleEtablissement";
    public static final String FIELD_COMPANY_ACTIVITY = "activitePrincipaleEtablissement";
    public static final String FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME = "nomenclatureActivitePrincipaleEtablissement";
    public static final String FIELD_COMPANY_IS_EMPLOYER = "caractereEmployeurEtablissement";
    public static final String[] HEADER = new String[]{
            FIELD_SIREN,
            FIELD_NIC,
            FIELD_SIRET,
            FIELD_COMPANY_DIFFUSION_STATUT,
            FIELD_COMPANY_CREATION_DATE,
            FIELD_COMPANY_STAFF_NUMBER_RANGE,
            FIELD_COMPANY_STAFF_NUMBER_YEAR,
            FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY,
            FIELD_COMPANY_LAST_PROCESSING,
            FIELD_COMPANY_IS_HEADQUARTERS,
            FIELD_COMPANY_PERIOD_COUNT,
            FIELD_COMPANY_ADDRESS_COMPLEMENT,
            FIELD_COMPANY_ADDRESS_STREET_NUMBER,
            FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION,
            FIELD_COMPANY_ADDRESS_STREET_TYPE,
            FIELD_COMPANY_ADDRESS_STREET_NAME,
            FIELD_COMPANY_ADDRESS_POSTAL_CODE,
            FIELD_COMPANY_ADDRESS_CITY,
            FIELD_COMPANY_FOREIGN_ADDRESS_CITY,
            FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION,
            FIELD_COMPANY_ADDRESS_CITY_CODE,
            FIELD_COMPANY_ADDRESS_CEDEX_CODE,
            FIELD_COMPANY_ADDRESS_CEDEX_NAME,
            FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE,
            FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME,
            FIELD_COMPANY_ADDRESS_COMPLEMENT_2,
            FIELD_COMPANY_ADDRESS_STREET_NUMBER_2,
            FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2,
            FIELD_COMPANY_ADDRESS_STREET_TYPE_2,
            FIELD_COMPANY_ADDRESS_STREET_NAME_2,
            FIELD_COMPANY_ADDRESS_POSTAL_CODE_2,
            FIELD_COMPANY_ADDRESS_CITY_2,
            FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2,
            FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2,
            FIELD_COMPANY_ADDRESS_CITY_CODE_2,
            FIELD_COMPANY_ADDRESS_CEDEX_CODE_2,
            FIELD_COMPANY_ADDRESS_CEDEX_NAME_2,
            FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2,
            FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2,
            FIELD_BEGIN_DATE,
            FIELD_COMPANY_ADMINISTATIVE_STATE,
            FIELD_COMPANY_NAME_1,
            FIELD_COMPANY_NAME_2,
            FIELD_COMPANY_NAME_3,
            FIELD_COMPANY_USUAL_NAME,
            FIELD_COMPANY_ACTIVITY,
            FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME,
            FIELD_COMPANY_IS_EMPLOYER,
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
