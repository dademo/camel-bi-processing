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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import static fr.dademo.bi.companies.jobs.stg.companies_history.entities.CompanyHistory.*;

@Dependent
@Named(CompaniesHistoryReader.BEAN_NAME)
public class CompaniesHistoryReader implements ItemReader {

    public static final String BEAN_NAME = "CompaniesHistoryReader";
    private static final Logger LOGGER = Logger.getLogger(CompaniesHistoryReader.class);
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockEtablissementHistorique_utf8.zip";

    @Inject
    HttpDataQuerier httpDataQuerier;

    private Iterator<CSVRecord> iterator;

    @Override
    public void open(Serializable checkpoint) throws Exception {

        LOGGER.info("Reading values");
        // Querying for values
        var queryUrl = new URL(DATASET_URL);
        httpDataQuerier.basicQuery(queryUrl, this::consumeResultStream);
        LOGGER.info("Parsed values");
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public Object readItem() throws Exception {

        return iterator.hasNext() ?
                iterator.next() :
                null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }

    @SneakyThrows
    private void consumeResultStream(InputStream inputStream) {

        var csvRecordEntries = new ArrayList<CSVRecord>();
        ArchiveEntry archiveEntry;
        try (var archiveInputStream = new ZipArchiveInputStream(inputStream)) {
            while ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                if (!archiveEntry.isDirectory()) {
                    csvFormat()
                            .parse(new InputStreamReader(archiveInputStream))
                            .forEach(csvRecordEntries::add);
                }
            }
        }
        iterator = csvRecordEntries.iterator();
    }

    private CSVFormat csvFormat() {

        return CSVFormat.DEFAULT.builder()
                .setHeader(HEADER)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
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
