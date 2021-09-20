package fr.dademo.bi.companies.jobs.stg.companies_history;

import fr.dademo.bi.companies.jobs.stg.companies_history.entities.CompanyHistory;
import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.companies_history.entities.CompanyHistory.*;

@Dependent
@Named(CompaniesHistoryReader.BEAN_NAME)
public class CompaniesHistoryReader implements ItemReader {

    public static final String BEAN_NAME = "CompaniesHistoryReader";
    private static final Logger LOGGER = Logger.getLogger(CompaniesHistoryReader.class);
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockEtablissementHistorique_utf8.zip";

    @Inject
    HttpDataQuerier httpDataQuerier;

    ZipArchiveInputStream archiveInputStream;
    ArchiveEntry archiveEntry;

    private Iterator<CSVRecord> iterator = Collections.emptyIterator();

    @Override
    public void open(Serializable checkpoint) throws Exception {

        LOGGER.info("Reading values");
        // Querying for values
        var queryUrl = new URL(DATASET_URL);

        archiveInputStream = new ZipArchiveInputStream(httpDataQuerier.basicQuery(queryUrl));
    }

    @Override
    public void close() throws Exception {
        archiveInputStream.close();
    }

    @Override
    public Object readItem() throws Exception {

        return nextItem().orElse(null);
    }

    @SneakyThrows
    private Optional<CSVRecord> nextItem() {

        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        } else {
            while (true) {
                if ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                    if (!archiveEntry.isDirectory()) {
                        iterator = getCsvStreamIterator();
                        if (iterator.hasNext()) {
                            return Optional.of(iterator.next());
                        }
                    }
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @SneakyThrows
    private Iterator<CSVRecord> getCsvStreamIterator() {

        return csvFormat()
                .parse(new InputStreamReader(archiveInputStream))
                .iterator();
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }

    private CSVFormat csvFormat() {

        return CSVFormat.DEFAULT.builder()
                .setHeader(HEADER)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }

    private CompanyHistory mapToCompanyHistory(CSVRecord csvRecord) {

        return CompanyHistory.builder()
                .siren(csvRecord.get(FIELD_SIREN))
                .nic(csvRecord.get(FIELD_NIC))
                .siret(csvRecord.get(FIELD_SIRET))
                //.endDate(FIELD_END_DATE)
                //.beginDate(FIELD_BEGIN_DATE)
                .institutionAdministrativeState(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE))
                .institutionNameChange(Boolean.parseBoolean(csvRecord.get(FIELD_INSTITUTION_ADMINISTRATIVE_STATE_CHANGE)))
                .institution1Name(csvRecord.get(FIELD_INSTITUTION_1_NAME))
                .institution2Name(csvRecord.get(FIELD_INSTITUTION_2_NAME))
                .institution3Name(csvRecord.get(FIELD_INSTITUTION_3_NAME))
                .institutionUsualNameChange(Boolean.parseBoolean(csvRecord.get(FIELD_INSTITUTION_NAME_CHANGE)))
                .institutionUsualName(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME))
                .institutionUsualNameChange(Boolean.parseBoolean(csvRecord.get(FIELD_INSTITUTION_USUAL_NAME_CHANGE)))
                .institutionPrimaryActivity(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY))
                .institutionPrimaryActivityNomenclature(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_NOMENCLATURE))
                .institutionPrimaryActivityChange(Boolean.parseBoolean(csvRecord.get(FIELD_INSTITUTION_PRIMARY_ACTIVITY_CHANGE)))
                .institutionEmployerNature(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE))
                .institutionEmployerNatureChange(Boolean.parseBoolean(csvRecord.get(FIELD_INSTITUTION_EMPLOYER_NATURE_CHANGE)))
                .build();
    }
}
