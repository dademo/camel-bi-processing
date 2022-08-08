/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.datamodel;

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
public class Association {

    public static final String CSV_FIELD_ASSOCIATION_ID = "id";
    public static final String CSV_FIELD_ASSOCIATION_ID_EX = "id_ex";
    public static final String CSV_FIELD_ASSOCIATION_SIRET = "siret";
    public static final String CSV_FIELD_ASSOCIATION_GESTION = "gestion";
    public static final String CSV_FIELD_ASSOCIATION_CREATION_DATE = "date_creat";
    public static final String CSV_FIELD_ASSOCIATION_PUBLICATION_DATE = "date_publi";
    public static final String CSV_FIELD_ASSOCIATION_NATURE = "nature";
    public static final String CSV_FIELD_ASSOCIATION_GROUPEMENT = "groupement";
    public static final String CSV_FIELD_ASSOCIATION_TITLE = "titre";
    public static final String CSV_FIELD_ASSOCIATION_OBJECT = "objet";
    public static final String CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_1 = "objet_social1";
    public static final String CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_2 = "objet_social2";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_1 = "adr1";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_2 = "adr2";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_3 = "adr3";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE = "adrs_codepostal";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE = "libcom";
    public static final String CSV_FIELD_ASSOCIATION_ADDRESS_INSEE_CODE = "adrs_codeinsee";
    public static final String CSV_FIELD_ASSOCIATION_LEADER_CIVILITY = "dir_civilite";
    public static final String CSV_FIELD_ASSOCIATION_WEBSITE = "siteweb";
    public static final String CSV_FIELD_ASSOCIATION_OBSERVATION = "observation";
    public static final String CSV_FIELD_ASSOCIATION_POSITION = "position";
    public static final String CSV_FIELD_ASSOCIATION_RUP_CODE = "rup_mi";
    public static final String CSV_FIELD_ASSOCIATION_LAST_UPDATED = "maj_time";

    public static final String[] CSV_HEADER_ASSOCIATION = new String[]{ // NOSONAR
        CSV_FIELD_ASSOCIATION_ID,
        CSV_FIELD_ASSOCIATION_ID_EX,
        CSV_FIELD_ASSOCIATION_SIRET,
        CSV_FIELD_ASSOCIATION_GESTION,
        CSV_FIELD_ASSOCIATION_CREATION_DATE,
        CSV_FIELD_ASSOCIATION_PUBLICATION_DATE,
        CSV_FIELD_ASSOCIATION_NATURE,
        CSV_FIELD_ASSOCIATION_GROUPEMENT,
        CSV_FIELD_ASSOCIATION_TITLE,
        CSV_FIELD_ASSOCIATION_OBJECT,
        CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_1,
        CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_2,
        CSV_FIELD_ASSOCIATION_ADDRESS_1,
        CSV_FIELD_ASSOCIATION_ADDRESS_2,
        CSV_FIELD_ASSOCIATION_ADDRESS_3,
        CSV_FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE,
        CSV_FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE,
        CSV_FIELD_ASSOCIATION_ADDRESS_INSEE_CODE,
        CSV_FIELD_ASSOCIATION_LEADER_CIVILITY,
        //CSV_FIELD_ASSOCIATION_TELEPHONE,
        CSV_FIELD_ASSOCIATION_WEBSITE,
        //CSV_FIELD_ASSOCIATION_EMAIL,
        CSV_FIELD_ASSOCIATION_OBSERVATION,
        CSV_FIELD_ASSOCIATION_POSITION,
        CSV_FIELD_ASSOCIATION_RUP_CODE,
        CSV_FIELD_ASSOCIATION_LAST_UPDATED,
    };

    private String id;
    private String idEx;
    private String siret;
    private String gestion;
    private LocalDate creationDate;
    private LocalDate publicationDate;
    private String nature;
    private String groupement;
    private String title;
    private String object;
    private String socialObject1;
    private String socialObject2;
    private String address1;
    private String address2;
    private String address3;
    private String addressPostalCode;
    private String addressInseeCode;
    private String addressCityLibelle;
    private String leaderCivility;
    private String website;
    private String observation;
    private String position;
    private String rupCode;
    private LocalDate lastUpdated;

    @AllArgsConstructor
    @Getter
    @Builder
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static class AssociationCsvColumnsMapping {

        int associationIdField;
        int associationIdExField;
        int associationSiretField;
        int associationGestionField;
        int associationCreationDateField;
        int associationPublicationDateField;
        int associationNatureField;
        int associationGroupementField;
        int associationTitleField;
        int associationObjectField;
        int associationSocialObject1Field;
        int associationSocialObject2Field;
        int associationAddress1Field;
        int associationAddress2Field;
        int associationAddress3Field;
        int associationAddressPostalCodeField;
        int associationAddressCityLibelleField;
        int associationAddressInseeCodeField;
        int associationLeaderCivilityField;
        //int associationTelephoneField;
        int associationWebsiteField;
        //int associationEmailField;
        int associationObservationField;
        int associationPositionField;
        int associationRupCodeField;
        int associationLastUpdatedField;
    }
}
