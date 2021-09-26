package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.jobs.stg.company.entities.CompanyEntity;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.mapper.RecordMapper;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;

import static fr.dademo.bi.companies.jobs.stg.company.entities.CompanyEntity.*;
import static fr.dademo.bi.companies.tools.batch.mapper.BatchMapperTools.*;

@ApplicationScoped
public class CompanyMapper implements RecordMapper<CSVRecord, CompanyEntity> {

    private static final Logger LOGGER = Logger.getLogger(CompanyMapper.class);

    @Override
    public Record<CompanyEntity> processRecord(Record<CSVRecord> item) {
        return toRecord(item.getHeader(), mappedToCompanyHistoryEntity(item.getPayload()));
    }

    private Record<CompanyEntity> toRecord(Header sourceHeader, CompanyEntity payload) {
        return new GenericRecord<>(
                new Header(
                        sourceHeader.getNumber(),
                        sourceHeader.getSource(),
                        LocalDateTime.now()),
                payload
        );
    }

    private CompanyEntity mappedToCompanyHistoryEntity(CSVRecord csvRecord) {

        return CompanyEntity.builder()
                .siren(csvRecord.get(FIELD_SIREN))
                .nic(csvRecord.get(FIELD_NIC))
                .siret(csvRecord.get(FIELD_SIRET))
                .companyDiffusionStatut(csvRecord.get(FIELD_COMPANY_DIFFUSION_STATUT))
                .companyCreationDate(toLocalDate(csvRecord.get(FIELD_COMPANY_CREATION_DATE)))
                .companyStaffNumberRange(csvRecord.get(FIELD_COMPANY_STAFF_NUMBER_RANGE))
                .companyStaffNumberYear(fromInteger(csvRecord.get(FIELD_COMPANY_STAFF_NUMBER_YEAR)))
                .companyPrincipalRegisteredActivity(csvRecord.get(FIELD_COMPANY_PRINCIPAL_REGISTERED_ACTIVITY))
                .companyLastProcessingTimestamp(toLocalDateTime(csvRecord.get(FIELD_COMPANY_LAST_PROCESSING)))
                .companyIsHeadquarters(fromBoolean(csvRecord.get(FIELD_COMPANY_IS_HEADQUARTERS)))
                .companyPeriodCount(fromInteger(csvRecord.get(FIELD_COMPANY_PERIOD_COUNT)))
                .companyAddressComplement(csvRecord.get(FIELD_COMPANY_ADDRESS_COMPLEMENT))
                .companyAddressStreetNumber(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NUMBER))
                .companyAddressStreetNumberRepetition(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION))
                .companyAddressStreetType(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_TYPE))
                .companyAddressStreetName(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NAME))
                .companyAddressPostalCode(csvRecord.get(FIELD_COMPANY_ADDRESS_POSTAL_CODE))
                .companyAddressCity(csvRecord.get(FIELD_COMPANY_ADDRESS_CITY))
                .companyForeignAddressCity(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_CITY))
                .companyAddressSpecialDistribution(csvRecord.get(FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION))
                .companyAddressCityCode(csvRecord.get(FIELD_COMPANY_ADDRESS_CITY_CODE))
                .companyAddressCedexCode(csvRecord.get(FIELD_COMPANY_ADDRESS_CEDEX_CODE))
                .companyAddressCedexName(csvRecord.get(FIELD_COMPANY_ADDRESS_CEDEX_NAME))
                .companyForeignAddressCountryCode(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE))
                .companyForeignAddressCountryName(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME))
                .companyAddressComplement2(csvRecord.get(FIELD_COMPANY_ADDRESS_COMPLEMENT_2))
                .companyAddressStreetNumber2(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NUMBER_2))
                .companyAddressStreetNumberRepetition2(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NUMBER_REPETITION_2))
                .companyAddressStreetType2(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_TYPE_2))
                .companyAddressStreetName2(csvRecord.get(FIELD_COMPANY_ADDRESS_STREET_NAME_2))
                .companyAddressPostalCode2(csvRecord.get(FIELD_COMPANY_ADDRESS_POSTAL_CODE_2))
                .companyAddressCity2(csvRecord.get(FIELD_COMPANY_ADDRESS_CITY_2))
                .companyForeignAddressCity2(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_CITY_2))
                .companyAddressSpecialDistribution2(csvRecord.get(FIELD_COMPANY_ADDRESS_SPECIAL_DISTRIBUTION_2))
                .companyAddressCityCode2(csvRecord.get(FIELD_COMPANY_ADDRESS_CITY_CODE_2))
                .companyAddressCedexCode2(csvRecord.get(FIELD_COMPANY_ADDRESS_CEDEX_CODE_2))
                .companyAddressCedexName2(csvRecord.get(FIELD_COMPANY_ADDRESS_CEDEX_NAME_2))
                .companyForeignAddressCountryCode2(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_CODE_2))
                .companyForeignAddressCountryName2(csvRecord.get(FIELD_COMPANY_FOREIGN_ADDRESS_COUNTRY_NAME_2))
                .beginDate(toLocalDate(csvRecord.get(FIELD_BEGIN_DATE)))
                .companyAdministativeState(csvRecord.get(FIELD_COMPANY_ADMINISTATIVE_STATE))
                .companyName1(csvRecord.get(FIELD_COMPANY_NAME_1))
                .companyName2(csvRecord.get(FIELD_COMPANY_NAME_2))
                .companyName3(csvRecord.get(FIELD_COMPANY_NAME_3))
                .companyUsualName(csvRecord.get(FIELD_COMPANY_USUAL_NAME))
                .companyActivity(csvRecord.get(FIELD_COMPANY_ACTIVITY))
                .companyPrincipalActivityName(csvRecord.get(FIELD_COMPANY_PRINCIPAL_ACTIVITY_NAME))
                .companyIsEmployer(csvRecord.get(FIELD_COMPANY_IS_EMPLOYER))
                .build();
    }
}
