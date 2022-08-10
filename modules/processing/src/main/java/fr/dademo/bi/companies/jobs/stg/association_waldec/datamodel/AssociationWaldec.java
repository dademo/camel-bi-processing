/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class AssociationWaldec {

    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ID = "id";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ID_EX = "id_ex";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_SIRET = "siret";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_RUP_CODE = "rup_mi";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION = "gestion";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_CREATION_DATE = "date_creat";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_DECLARATION_DATE = "date_decla";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_PUBLICATION_DATE = "date_publi";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_DISSOLUTION_DATE = "date_disso";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_NATURE = "nature";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GROUPEMENT = "groupement";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_TITLE = "titre";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_TITLE_SHORT = "titre_court";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_OBJECT = "objet";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_1 = "objet_social1";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_2 = "objet_social2";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_COMPLEMENT = "adrs_complement";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_NUMBER = "adrs_numvoie";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_REPETITION = "adrs_repetition";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_TYPE = "adrs_typevoie";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_LIBELLE = "adrs_libvoie";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_DISTRIBUTION = "adrs_distrib";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_POSTAL_CODE = "adrs_codepostal";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_INSEE_CODE = "adrs_codeinsee";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_CITY_LIBELLE = "adrs_libcommune";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_DECLARANT_SURNAME = "adrg_declarant";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_ID = "adrg_complemid";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_GEO = "adrg_complemgeo";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_STREET_LIBELLE = "adrg_libvoie";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_DISTRIBUTION = "adrg_distrib";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_POSTAL_CODE = "adrg_codepostal";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_FORWARD = "adrg_achemine";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_GESTION_COUNTRY = "adrg_pays";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_LEADER_CIVILITY = "dir_civilite";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_WEBSITE = "siteweb";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_PUBLIC_WEBSITE = "publiweb";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_OBSERVATION = "observation";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_POSITION = "position";
    public static final String CSV_FIELD_ASSOCIATION_WALDEC_LAST_UPDATED = "maj_time";

    public static final String[] CSV_HEADER_ASSOCIATION_WALDEC = new String[]{ // NOSONAR
        CSV_FIELD_ASSOCIATION_WALDEC_ID,
        CSV_FIELD_ASSOCIATION_WALDEC_ID_EX,
        CSV_FIELD_ASSOCIATION_WALDEC_SIRET,
        CSV_FIELD_ASSOCIATION_WALDEC_RUP_CODE,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION,
        CSV_FIELD_ASSOCIATION_WALDEC_CREATION_DATE,
        CSV_FIELD_ASSOCIATION_WALDEC_DECLARATION_DATE,
        CSV_FIELD_ASSOCIATION_WALDEC_PUBLICATION_DATE,
        CSV_FIELD_ASSOCIATION_WALDEC_DISSOLUTION_DATE,
        CSV_FIELD_ASSOCIATION_WALDEC_NATURE,
        CSV_FIELD_ASSOCIATION_WALDEC_GROUPEMENT,
        CSV_FIELD_ASSOCIATION_WALDEC_TITLE,
        CSV_FIELD_ASSOCIATION_WALDEC_TITLE_SHORT,
        CSV_FIELD_ASSOCIATION_WALDEC_OBJECT,
        CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_1,
        CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_2,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_COMPLEMENT,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_NUMBER,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_REPETITION,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_TYPE,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_LIBELLE,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_DISTRIBUTION,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_POSTAL_CODE,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_INSEE_CODE,
        CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_CITY_LIBELLE,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_DECLARANT_SURNAME,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_ID,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_GEO,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_STREET_LIBELLE,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_DISTRIBUTION,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_POSTAL_CODE,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_FORWARD,
        CSV_FIELD_ASSOCIATION_WALDEC_GESTION_COUNTRY,
        CSV_FIELD_ASSOCIATION_WALDEC_LEADER_CIVILITY,
        CSV_FIELD_ASSOCIATION_WALDEC_WEBSITE,
        CSV_FIELD_ASSOCIATION_WALDEC_PUBLIC_WEBSITE,
        CSV_FIELD_ASSOCIATION_WALDEC_OBSERVATION,
        CSV_FIELD_ASSOCIATION_WALDEC_POSITION,
        CSV_FIELD_ASSOCIATION_WALDEC_LAST_UPDATED,
    };

    private String id;
    private String idEx;
    private String siret;
    private String rupCode;
    private String gestion;
    private LocalDate creationDate;
    private LocalDate declarationDate;
    private LocalDate publicationDate;
    private LocalDate dissolutionDate;
    private String nature;
    private String groupement;
    private String title;
    private String titleShort;
    private String object;
    private String socialObject1;
    private String socialObject2;
    private String addressComplement;
    private String addressStreetNumber;
    private String addressRepetition;
    private String addressStreetType;
    private String addressStreetLibelle;
    private String addressDistribution;
    private String addressPostalCode;
    private String addressInseeCode;
    private String addressCityLibelle;
    private String gestionDeclarantSurname;
    private String gestionAddressComplementId;
    private String gestionAddressComplementGeo;
    private String gestionAddressStreetLibelle;
    private String gestionAddressDistribution;
    private String gestionAddressPostalCode;
    private String gestionForward;
    private String gestionCountry;
    private String leaderCivility;
    private String website;
    private String publicWebsite;
    private String observation;
    private String position;
    private LocalDateTime lastUpdated;


    @AllArgsConstructor
    @Getter
    @Builder
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class AssociationWaldecCsvColumnsMapping {

        int idField;
        int idExField;
        int siretField;
        int rupCodeField;
        int gestionField;
        int creationDateField;
        int declarationDateField;
        int publicationDateField;
        int dissolutionDateField;
        int natureField;
        int groupementField;
        int titleField;
        int titleShortField;
        int objectField;
        int socialObject1Field;
        int socialObject2Field;
        int addressComplementField;
        int addressStreetNumberField;
        int addressRepetitionField;
        int addressStreetTypeField;
        int addressStreetLibelleField;
        int addressDistributionField;
        int addressPostalCodeField;
        int addressInseeCodeField;
        int addressCityLibelleField;
        int gestionDeclarantSurnameField;
        int gestionAddressComplementIdField;
        int gestionAddressComplementGeoField;
        int gestionAddressStreetLibelleField;
        int gestionAddressDistributionField;
        int gestionAddressPostalCodeField;
        int gestionForwardField;
        int gestionCountryField;
        int leaderCivilityField;
        int websiteField;
        int publicWebsiteField;
        int observationField;
        int position;
        int lastUpdated;
    }
}
