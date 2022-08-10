/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.mapper.BatchMapperTools;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Optional;

import static fr.dademo.batch.tools.batch.mapper.BatchMapperTools.toLocalDate;
import static fr.dademo.bi.companies.jobs.stg.association.datamodel.Association.*;

/**
 * @author dademo
 */
@Component
public class AssociationItemMapper implements ItemProcessor<WrappedRowResource, Association> {

    private AssociationCsvColumnsMapping columnsIndexMapping;

    @Override
    public Association process(@Nonnull WrappedRowResource item) {
        return mappedToCompanyHistory(item);
    }

    private Association mappedToCompanyHistory(WrappedRowResource item) {

        if (columnsIndexMapping == null) {
            synchronized (this) {
                // Filling the mapping
                columnsIndexMapping = getHeaderMapping(item);
            }
        }

        return Association.builder()
            .id(item.getString(columnsIndexMapping.getAssociationIdField()))
            .idEx(item.getString(columnsIndexMapping.getAssociationIdExField()))
            .siret(item.getString(columnsIndexMapping.getAssociationSiretField()))
            .gestion(item.getString(columnsIndexMapping.getAssociationGestionField()))
            .creationDate(toLocalDate(item.getString(columnsIndexMapping.getAssociationCreationDateField())))
            .publicationDate(toLocalDate(item.getString(columnsIndexMapping.getAssociationPublicationDateField())))
            .nature(item.getString(columnsIndexMapping.getAssociationNatureField()))
            .groupement(item.getString(columnsIndexMapping.getAssociationGroupementField()))
            .title(item.getString(columnsIndexMapping.getAssociationTitleField()))
            .object(item.getString(columnsIndexMapping.getAssociationObjectField()))
            .socialObject1(item.getString(columnsIndexMapping.getAssociationSocialObject1Field()))
            .socialObject2(item.getString(columnsIndexMapping.getAssociationSocialObject2Field()))
            .address1(item.getString(columnsIndexMapping.getAssociationAddress1Field()))
            .address2(item.getString(columnsIndexMapping.getAssociationAddress2Field()))
            .address3(item.getString(columnsIndexMapping.getAssociationAddress3Field()))
            .addressPostalCode(item.getString(columnsIndexMapping.getAssociationAddressPostalCodeField()))
            .addressInseeCode(item.getString(columnsIndexMapping.getAssociationAddressInseeCodeField()))
            .addressCityLibelle(item.getString(columnsIndexMapping.getAssociationAddressCityLibelleField()))
            .leaderCivility(item.getString(columnsIndexMapping.getAssociationLeaderCivilityField()))
            //.telephone(item.getString(columnsIndexMapping.getAssociationTelephoneField()))
            .website(item.getString(columnsIndexMapping.getAssociationWebsiteField()))
            //.email(item.getString(columnsIndexMapping.getAssociationEmailField()))
            .observation(item.getString(columnsIndexMapping.getAssociationObservationField()))
            .position(item.getString(columnsIndexMapping.getAssociationPositionField()))
            .rupCode(item.getString(columnsIndexMapping.getAssociationRupCodeField()))
            .lastUpdated(parseLastUpdatedDate(item.getString(columnsIndexMapping.getAssociationLastUpdatedField())))
            .build();
    }

    private AssociationCsvColumnsMapping getHeaderMapping(WrappedRowResource item) {

        return AssociationCsvColumnsMapping.builder()
            .associationIdField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ID))
            .associationIdExField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ID_EX))
            .associationSiretField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_SIRET))
            .associationGestionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_GESTION))
            .associationCreationDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_CREATION_DATE))
            .associationPublicationDateField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_PUBLICATION_DATE))
            .associationNatureField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_NATURE))
            .associationGroupementField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_GROUPEMENT))
            .associationTitleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_TITLE))
            .associationObjectField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_OBJECT))
            .associationSocialObject1Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_1))
            .associationSocialObject2Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_2))
            .associationAddress1Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_1))
            .associationAddress2Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_2))
            .associationAddress3Field(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_3))
            .associationAddressPostalCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE))
            .associationAddressCityLibelleField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE))
            .associationAddressInseeCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_ADDRESS_INSEE_CODE))
            .associationLeaderCivilityField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_LEADER_CIVILITY))
            //.associationTelephoneField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_TELEPHONE))
            .associationWebsiteField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_WEBSITE))
            //.associationEmailField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_EMAIL))
            .associationObservationField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_OBSERVATION))
            .associationPositionField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_POSITION))
            .associationRupCodeField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_RUP_CODE))
            .associationLastUpdatedField(item.getColumnIndexByName(CSV_FIELD_ASSOCIATION_LAST_UPDATED))
            .build();
    }

    private LocalDate parseLastUpdatedDate(@Nullable String dateStr) {

        return Optional.ofNullable(dateStr)
            .map(d -> d.substring(0, 10))
            .map(BatchMapperTools::toLocalDate)
            .orElse(null);
    }
}
