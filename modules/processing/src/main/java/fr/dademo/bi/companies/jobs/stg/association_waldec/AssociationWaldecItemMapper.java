/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toLocalDate;
import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toLocalDateTimeUsingPattern;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec.*;

/**
 * @author dademo
 */
@Component
public class AssociationWaldecItemMapper implements ItemProcessor<WrappedRowResource, AssociationWaldec> {

    public static final String ASSOCIATION_WALDEC_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private AssociationWaldecCsvColumnsMapping columnsIndexMapping;

    @Override
    public AssociationWaldec process(@Nonnull WrappedRowResource item) {
        return mappedToAssociationWaldec(item);
    }

    private AssociationWaldec mappedToAssociationWaldec(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return AssociationWaldec.builder()
            .id(item.getString(columnsIndexMapping.getIdField()))
            .idEx(item.getString(columnsIndexMapping.getIdExField()))
            .siret(item.getString(columnsIndexMapping.getSiretField()))
            .rupCode(item.getString(columnsIndexMapping.getRupCodeField()))
            .gestion(item.getString(columnsIndexMapping.getGestionField()))
            .creationDate(toLocalDate(item.getString(columnsIndexMapping.getCreationDateField())))
            .declarationDate(toLocalDate(item.getString(columnsIndexMapping.getDeclarationDateField())))
            .publicationDate(toLocalDate(item.getString(columnsIndexMapping.getPublicationDateField())))
            .dissolutionDate(toLocalDate(item.getString(columnsIndexMapping.getDissolutionDateField())))
            .nature(item.getString(columnsIndexMapping.getNatureField()))
            .groupement(item.getString(columnsIndexMapping.getGroupementField()))
            .title(item.getString(columnsIndexMapping.getTitleField()))
            .titleShort(item.getString(columnsIndexMapping.getTitleShortField()))
            .object(item.getString(columnsIndexMapping.getObjectField()))
            .socialObject1(item.getString(columnsIndexMapping.getSocialObject1Field()))
            .socialObject2(item.getString(columnsIndexMapping.getSocialObject2Field()))
            .addressComplement(item.getString(columnsIndexMapping.getAddressComplementField()))
            .addressStreetNumber(item.getString(columnsIndexMapping.getAddressStreetNumberField()))
            .addressRepetition(item.getString(columnsIndexMapping.getAddressRepetitionField()))
            .addressStreetType(item.getString(columnsIndexMapping.getAddressStreetTypeField()))
            .addressStreetLibelle(item.getString(columnsIndexMapping.getAddressStreetLibelleField()))
            .addressDistribution(item.getString(columnsIndexMapping.getAddressDistributionField()))
            .addressPostalCode(item.getString(columnsIndexMapping.getAddressPostalCodeField()))
            .addressInseeCode(item.getString(columnsIndexMapping.getAddressInseeCodeField()))
            .addressCityLibelle(item.getString(columnsIndexMapping.getAddressCityLibelleField()))
            .gestionDeclarantSurname(item.getString(columnsIndexMapping.getGestionDeclarantSurnameField()))
            .gestionAddressComplementId(item.getString(columnsIndexMapping.getGestionAddressComplementIdField()))
            .gestionAddressComplementGeo(item.getString(columnsIndexMapping.getGestionAddressComplementGeoField()))
            .gestionAddressStreetLibelle(item.getString(columnsIndexMapping.getGestionAddressStreetLibelleField()))
            .gestionAddressDistribution(item.getString(columnsIndexMapping.getGestionAddressDistributionField()))
            .gestionAddressPostalCode(item.getString(columnsIndexMapping.getGestionAddressPostalCodeField()))
            .gestionForward(item.getString(columnsIndexMapping.getGestionForwardField()))
            .gestionCountry(item.getString(columnsIndexMapping.getGestionCountryField()))
            .leaderCivility(item.getString(columnsIndexMapping.getLeaderCivilityField()))
            .website(item.getString(columnsIndexMapping.getWebsiteField()))
            .publicWebsite(item.getString(columnsIndexMapping.getPublicWebsiteField()))
            .observation(item.getString(columnsIndexMapping.getObservationField()))
            .position(item.getString(columnsIndexMapping.getPosition()))
            .lastUpdated(toLocalDateTimeUsingPattern(item.getString(columnsIndexMapping.getLastUpdated()), ASSOCIATION_WALDEC_DATE_TIME_PATTERN))
            .build();
    }

    private AssociationWaldecCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return AssociationWaldecCsvColumnsMapping.builder()
            .idField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ID))
            .idExField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ID_EX))
            .siretField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_SIRET))
            .rupCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_RUP_CODE))
            .gestionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION))
            .creationDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_CREATION_DATE))
            .declarationDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_DECLARATION_DATE))
            .publicationDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_PUBLICATION_DATE))
            .dissolutionDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_DISSOLUTION_DATE))
            .natureField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_NATURE))
            .groupementField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GROUPEMENT))
            .titleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_TITLE))
            .titleShortField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_TITLE_SHORT))
            .objectField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_OBJECT))
            .socialObject1Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_1))
            .socialObject2Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_2))
            .addressComplementField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_COMPLEMENT))
            .addressStreetNumberField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_NUMBER))
            .addressRepetitionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_REPETITION))
            .addressStreetTypeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_TYPE))
            .addressStreetLibelleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_LIBELLE))
            .addressDistributionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_DISTRIBUTION))
            .addressPostalCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_POSTAL_CODE))
            .addressInseeCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_INSEE_CODE))
            .addressCityLibelleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_CITY_LIBELLE))
            .gestionDeclarantSurnameField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_DECLARANT_SURNAME))
            .gestionAddressComplementIdField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_ID))
            .gestionAddressComplementGeoField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_GEO))
            .gestionAddressStreetLibelleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_STREET_LIBELLE))
            .gestionAddressDistributionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_DISTRIBUTION))
            .gestionAddressPostalCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_POSTAL_CODE))
            .gestionForwardField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_FORWARD))
            .gestionCountryField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_COUNTRY))
            .leaderCivilityField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_LEADER_CIVILITY))
            .websiteField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_WEBSITE))
            .publicWebsiteField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_PUBLIC_WEBSITE))
            .observationField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_OBSERVATION))
            .position(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_POSITION))
            .lastUpdated(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WALDEC_LAST_UPDATED))
            .build();
    }
}
