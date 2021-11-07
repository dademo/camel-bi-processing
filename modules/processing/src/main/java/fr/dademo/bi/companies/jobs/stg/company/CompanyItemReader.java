package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.bi.companies.tools.batch.reader.HttpItemStreamReaderSupport;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.company.datamodel.Company.CSV_HEADER_COMPANY;

@Component
@SuppressWarnings("java:S112")
public class CompanyItemReader extends HttpItemStreamReaderSupport<CSVRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyItemReader.class);

    private static final String DATASET_TITLE = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATA_TITLE_PREFIX = "Sirene : Fichier StockEtablissement";

    @Autowired
    private DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private ZipArchiveInputStream archiveInputStream;
    private Iterator<CSVRecord> iterator = Collections.emptyIterator();

    @SneakyThrows
    public void open(@Nonnull ExecutionContext executionContext) {

        LOGGER.info("Getting dataset definition");
        final var dataGouvFrDataSet = dataGouvFrDataQuerierService.getDataSet(DATASET_TITLE);
        final var dataGouvFrDataSetResource = dataGouvFrDataSet
                .getResources().stream()
                .filter(DataGouvFrFilterHelpers.fieldStartingWith(DataGouvFrDataSetResource::getTitle, DATA_TITLE_PREFIX))
                .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
                .orElseThrow(() -> new ResourceNotFoundException(DATA_TITLE_PREFIX + "*", dataGouvFrDataSet));

        LOGGER.info("Reading values");
        archiveInputStream = new ZipArchiveInputStream(dataGouvFrDataQuerierService.queryForStream(dataGouvFrDataSetResource));
    }

    @Override
    @SneakyThrows
    public void close() {
        archiveInputStream.close();
    }

    @Override
    public CSVRecord read() {

        return nextItem().orElse(null);
    }

    @SneakyThrows
    private synchronized Optional<CSVRecord> nextItem() {   // NOSONAR

        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        } else {
            while (true) {
                ArchiveEntry archiveEntry;
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

    private CSVFormat csvFormat() {

        return CSVFormat.DEFAULT.builder()
                .setHeader(CSV_HEADER_COMPANY)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }
}
