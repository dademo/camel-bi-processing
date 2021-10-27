package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.services.DataGouvFrDataSetTools;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance.CSV_HEADER_COMPANY_INHERITANCE;

@Component
public class CompanyInheritanceReader implements ItemReader<CSVRecord> {

    private static final Logger LOGGER = Logger.getLogger(CompanyInheritanceReader.class);
    private static final String DATASET_NAME = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATASET_URL = "https://files.data.gouv.fr/insee-sirene/StockEtablissementLiensSuccession_utf8.zip";

    @Autowired
    private HttpDataQuerier httpDataQuerier;

    @Autowired
    private DataGouvFrDataSetTools dataGouvFrDataSetTools;

    private Iterator<CSVRecord> iterator = Collections.emptyIterator();
    private ZipArchiveInputStream archiveInputStream;

    @BeforeRead
    @SneakyThrows
    public void open() {

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

    @AfterRead
    @SneakyThrows
    public void close() {
        archiveInputStream.close();
    }

    @Override
    public synchronized CSVRecord read() {

        return nextItem().orElse(null);
    }

    @SneakyThrows
    private Optional<CSVRecord> nextItem() {    // NOSONAR

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
                .setHeader(CSV_HEADER_COMPANY_INHERITANCE)
                .setSkipHeaderRecord(true)
                .setDelimiter(",")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }
}
