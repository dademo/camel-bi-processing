package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools;
import org.apache.commons.csv.CSVRecord;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.Association.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.toLocalDate;

@ApplicationScoped
public class AssociationMapper implements RecordMapper<CSVRecord, Association> {

    @Override
    public Record<Association> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyHistory(item.getPayload()));
    }

    private Record<Association> toRecord(Header sourceHeader, Association payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
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
