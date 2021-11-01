package fr.dademo.bi.companies.jobs.stg.company_legal_history;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.services.DataGouvFrDataSetTools;
import fr.dademo.bi.companies.tools.batch.reader.HttpItemStreamReaderSupport;
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
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.company_legal_history.datamodel.CompanyLegalHistory.CSV_HEADER_COMPANY_LEGAL_HISTORY;

@Component
public class CompanyLegalHistoryItemReader extends HttpItemStreamReaderSupport<CSVRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyLegalHistoryItemReader.class);
    private static final String DATASET_NAME = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockUniteLegaleHistorique_utf8.zip";

    @Autowired
    private HttpDataQuerier httpDataQuerier;

    @Autowired
    private DataGouvFrDataSetTools dataGouvFrDataSetTools;

    private ZipArchiveInputStream archiveInputStream;
    private Iterator<CSVRecord> iterator = Collections.emptyIterator();

    @SneakyThrows
    public void open(@Nonnull ExecutionContext executionContext) {

        LOGGER.info("Reading values");

        final var queryUrl = new URL(DATASET_URL);

        archiveInputStream = new ZipArchiveInputStream(httpDataQuerier.basicQuery(
                queryUrl,
                Stream.of(dataGouvFrDataSetTools.hashDefinitionOfDataSetResourceByUrl(DATASET_NAME, DATASET_URL, false))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList())
        ));
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
                .setHeader(CSV_HEADER_COMPANY_LEGAL_HISTORY)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }
}
