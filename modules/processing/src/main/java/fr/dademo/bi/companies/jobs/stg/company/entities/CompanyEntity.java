package fr.dademo.bi.companies.jobs.stg.company.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RegisterForReflection
@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyEntity {

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "siren", length = 9, nullable = false)
    private String siren;

    @NotBlank
    @Column(name = "nic", length = 5, nullable = false)
    private String nic;


    @NotBlank
    @Column(name = "siret", length = 14, nullable = false)
    private String siret;


    @Column(name = "company_diffusion_statut", length = 1)
    private String companyDiffusionStatut;


    @Column(name = "company_creation_date")
    private LocalDate companyCreationDate;


    @Column(name = "company_staff_number_range", length = 2)
    private String companyStaffNumberRange;


    @Column(name = "company_staff_number_year")
    private Integer companyStaffNumberYear;


    @Column(name = "company_principal_registered_activity", length = 6)
    private String companyPrincipalRegisteredActivity;


    @Column(name = "company_last_processing")
    private LocalDateTime companyLastProcessingTimestamp;


    @Column(name = "company_is_headquarters")
    private Boolean companyIsHeadquarters;


    @Column(name = "company_period_count")
    private Integer companyPeriodCount;


    @Column(name = "company_address_complement", length = 38)
    private String companyAddressComplement;


    @Column(name = "company_address_street_number", length = 4)
    private String companyAddressStreetNumber;


    @Column(name = "company_address_street_number_repetition", length = 1)
    private String companyAddressStreetNumberRepetition;


    @Column(name = "company_address_street_type", length = 4)
    private String companyAddressStreetType;


    @Column(name = "company_address_street_name", length = 100)
    private String companyAddressStreetName;


    @Column(name = "company_address_postal_code", length = 5)
    private String companyAddressPostalCode;


    @Column(name = "company_address_city", length = 100)
    private String companyAddressCity;


    @Column(name = "company_foreign_address_city", length = 100)
    private String companyForeignAddressCity;


    @Column(name = "company_address_special_distribution", length = 26)
    private String companyAddressSpecialDistribution;


    @Column(name = "company_address_city_code", length = 5)
    private String companyAddressCityCode;


    @Column(name = "company_address_cedex_code", length = 9)
    private String companyAddressCedexCode;


    @Column(name = "company_address_cedex_name", length = 100)
    private String companyAddressCedexName;


    @Column(name = "company_foreign_address_country_code", length = 5)
    private String companyForeignAddressCountryCode;


    @Column(name = "company_foreign_address_country_name", length = 100)
    private String companyForeignAddressCountryName;


    @Column(name = "company_address_complement_2", length = 38)
    private String companyAddressComplement2;


    @Column(name = "company_address_street_number_2", length = 4)
    private String companyAddressStreetNumber2;


    @Column(name = "company_address_street_number_repetition_2", length = 1)
    private String companyAddressStreetNumberRepetition2;


    @Column(name = "company_address_street_type_2", length = 4)
    private String companyAddressStreetType2;


    @Column(name = "company_address_street_name_2", length = 100)
    private String companyAddressStreetName2;


    @Column(name = "company_address_postal_code_2", length = 5)
    private String companyAddressPostalCode2;


    @Column(name = "company_address_city_2", length = 100)
    private String companyAddressCity2;


    @Column(name = "company_foreign_address_city_2", length = 100)
    private String companyForeignAddressCity2;


    @Column(name = "company_address_special_distribution_2", length = 26)
    private String companyAddressSpecialDistribution2;


    @Column(name = "company_address_city_code_2", length = 5)
    private String companyAddressCityCode2;


    @Column(name = "company_address_cedex_code_2", length = 9)
    private String companyAddressCedexCode2;


    @Column(name = "company_address_cedex_name_2", length = 100)
    private String companyAddressCedexName2;


    @Column(name = "company_foreign_address_country_code_2", length = 5)
    private String companyForeignAddressCountryCode2;


    @Column(name = "company_foreign_address_country_name_2", length = 100)
    private String companyForeignAddressCountryName2;


    @Column(name = "begin_date")
    private LocalDate beginDate;


    @Column(name = "company_administative_state", length = 1)
    private String companyAdministativeState;


    @Column(name = "company_name_1", length = 50)
    private String companyName1;


    @Column(name = "company_name_2", length = 50)
    private String companyName2;


    @Column(name = "company_name_3", length = 50)
    private String companyName3;


    @Column(name = "company_usual_name", length = 100)
    private String companyUsualName;


    @Column(name = "company_activity", length = 6)
    private String companyActivity;


    @Column(name = "company_principal_activity_name", length = 8)
    private String companyPrincipalActivityName;


    @Column(name = "company_is_employer", length = 1)
    private String companyIsEmployer;
}
