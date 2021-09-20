package fr.dademo.bi.companies.jobs.stg.companies_history;

import fr.dademo.bi.companies.jobs.stg.companies_history.entities.CompanyHistory;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.companies_history.entities.CompanyHistory.*;

@Dependent
@Named(CompaniesHistoryProcessor.BEAN_NAME)
public class CompaniesHistoryProcessor implements ItemProcessor {

    public static final String BEAN_NAME = "CompaniesHistoryProcessor";
    private static final Logger LOGGER = Logger.getLogger(CompaniesHistoryProcessor.class);

    @Override
    public Object processItem(Object item) throws Exception {
        return mappedToCompanyHistory((CSVRecord) item);
    }

    private CompanyHistory mappedToCompanyHistory(CSVRecord csvRecord) {

        return CompanyHistory.builder()
                .siren(csvRecord.get(FIELD_SIREN))
                .nic(csvRecord.get(FIELD_NIC))
                .siret(csvRecord.get(FIELD_SIRET))
                .endDate(Optional.ofNullable(csvRecord.get(FIELD_END_DATE)).map(this::toLocalDate).orElse(null))
                .beginDate(Optional.ofNullable(csvRecord.get(FIELD_BEGIN_DATE)).map(this::toLocalDate).orElse(null))
                .institutionAdministrativeState(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE))
                .institutionAdministrativeStateChange(Optional.ofNullable(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE)).map(this::fromBoolean).orElse(null))
                .institution1Name(csvRecord.get(FIELD_INSTITUTION_1_NAME))
                .institution2Name(csvRecord.get(FIELD_INSTITUTION_2_NAME))
                .institution3Name(csvRecord.get(FIELD_INSTITUTION_3_NAME))
                .institutionNameChange(Optional.ofNullable(csvRecord.get(FIELD_INSTITUTION_NAME_CHANGE)).map(this::fromBoolean).orElse(null))
                .institutionUsualName(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME))
                .institutionUsualNameChange(Optional.ofNullable(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME_CHANGE)).map(this::fromBoolean).orElse(null))
                .institutionPrimaryActivity(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY))
                .institutionPrimaryActivityNomenclature(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE))
                .institutionPrimaryActivityChange(Optional.ofNullable(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE)).map(this::fromBoolean).orElse(null))
                .institutionEmployerNature(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE))
                .institutionEmployerNatureChange(Optional.ofNullable(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE)).map(this::fromBoolean).orElse(null))
                .build();
    }

    private Boolean fromBoolean(String str) {
        return Boolean.parseBoolean(str);
    }

    private LocalDate toLocalDate(String str) {
        return LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(str));
    }

    private LocalDateTime toLocalDateTime(String str) {
        return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(str));
    }
}
