/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec;

import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import org.apache.commons.csv.CSVRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toLocalDate;

/**
 * @author dademo
 */
@Component
public class AssociationWaldecItemMapper implements ItemProcessor<CSVRecord, AssociationWaldec> {

    @Override
    public AssociationWaldec process(@Nonnull CSVRecord item) {
        return mappedToCompanyHistory(item);
    }

    private AssociationWaldec mappedToCompanyHistory(CSVRecord csvRecord) {

        return AssociationWaldec.builder()
            .id(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ID))
            .idEx(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ID_EX))
            .siret(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_SIRET))
            .rupCode(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_RUP_CODE))
            .gestion(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION))
            .creationDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_CREATION_DATE)))
            .declarationDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_DECLARATION_DATE)))
            .publicationDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_PUBLICATION_DATE)))
            .dissolutionDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_DISSOLUTION_DATE)))
            .nature(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_NATURE))
            .groupement(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GROUPEMENT))
            .title(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_TITLE))
            .titleShort(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_TITLE_SHORT))
            .object(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_OBJECT))
            .socialObject1(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_1))
            .socialObject2(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_SOCIAL_OBJECT_2))
            .addressComplement(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_COMPLEMENT))
            .addressStreetNumber(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_NUMBER))
            .addressRepetition(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_REPETITION))
            .addressStreetType(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_TYPE))
            .addressStreetLibelle(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_STREET_LIBELLE))
            .addressDistribution(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_DISTRIBUTION))
            .addressPostalCode(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_POSTAL_CODE))
            .addressInseeCode(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_INSEE_CODE))
            .addressCityLibelle(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_ADDRESS_CITY_LIBELLE))
            .gestionDeclarantSurname(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_DECLARANT_SURNAME))
            .gestionAddressComplementId(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_ID))
            .gestionAddressComplementGeo(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_COMPLEMENT_GEO))
            .gestionAddressStreetLibelle(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_STREET_LIBELLE))
            .gestionAddressDistribution(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_DISTRIBUTION))
            .gestionAddressPostalCode(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_ADDRESS_POSTAL_CODE))
            .gestionForward(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_FORWARD))
            .gestionCountry(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_GESTION_COUNTRY))
            .leaderCivility(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_LEADER_CIVILITY))
            .phone(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_PHONE))
            .website(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_WEBSITE))
            .email(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_EMAIL))
            .publicWebsite(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_PUBLIC_WEBSITE))
            .observation(csvRecord.get(CSV_FIELD_ASSOCIATION_WALDEC_OBSERVATION))
            .build();
    }
}
