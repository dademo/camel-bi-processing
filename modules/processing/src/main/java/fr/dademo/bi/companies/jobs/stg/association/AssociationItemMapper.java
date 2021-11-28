/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.tools.batch.mapper.BatchMapperTools;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import org.apache.commons.csv.CSVRecord;
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
public class AssociationItemMapper implements ItemProcessor<CSVRecord, Association> {

    @Override
    public Association process(@Nonnull CSVRecord item) {
        return mappedToCompanyHistory(item);
    }

    private Association mappedToCompanyHistory(CSVRecord csvRecord) {

        return Association.builder()
            .id(csvRecord.get(CSV_FIELD_ASSOCIATION_ID))
            .idEx(csvRecord.get(CSV_FIELD_ASSOCIATION_ID_EX))
            .siret(csvRecord.get(CSV_FIELD_ASSOCIATION_SIRET))
            .gestion(csvRecord.get(CSV_FIELD_ASSOCIATION_GESTION))
            .creationDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_CREATION_DATE)))
            .publicationDate(toLocalDate(csvRecord.get(CSV_FIELD_ASSOCIATION_PUBLICATION_DATE)))
            .nature(csvRecord.get(CSV_FIELD_ASSOCIATION_NATURE))
            .groupement(csvRecord.get(CSV_FIELD_ASSOCIATION_GROUPEMENT))
            .title(csvRecord.get(CSV_FIELD_ASSOCIATION_TITLE))
            .object(csvRecord.get(CSV_FIELD_ASSOCIATION_OBJECT))
            .socialObject1(csvRecord.get(CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_1))
            .socialObject2(csvRecord.get(CSV_FIELD_ASSOCIATION_SOCIAL_OBJECT_2))
            .address1(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_1))
            .address2(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_2))
            .address3(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_3))
            .addressPostalCode(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_POSTAL_CODE))
            .addressInseeCode(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_INSEE_CODE))
            .addressCityLibelle(csvRecord.get(CSV_FIELD_ASSOCIATION_ADDRESS_CITY_LIBELLE))
            .leaderCivility(csvRecord.get(CSV_FIELD_ASSOCIATION_LEADER_CIVILITY))
            //.telephone(csvRecord.get(CSV_FIELD_ASSOCIATION_TELEPHONE))
            .website(csvRecord.get(CSV_FIELD_ASSOCIATION_WEBSITE))
            //.email(csvRecord.get(CSV_FIELD_ASSOCIATION_EMAIL))
            .observation(csvRecord.get(CSV_FIELD_ASSOCIATION_OBSERVATION))
            .position(csvRecord.get(CSV_FIELD_ASSOCIATION_POSITION))
            .rupCode(csvRecord.get(CSV_FIELD_ASSOCIATION_RUP_CODE))
            .lastUpdated(parseLastUpdatedDate(csvRecord.get(CSV_FIELD_ASSOCIATION_LAST_UPDATED)))
            .build();
    }

    private LocalDate parseLastUpdatedDate(@Nullable String dateStr) {

        return Optional.ofNullable(dateStr)
            .map(d -> d.substring(0, 10))
            .map(BatchMapperTools::toLocalDate)
            .orElse(null);
    }
}
