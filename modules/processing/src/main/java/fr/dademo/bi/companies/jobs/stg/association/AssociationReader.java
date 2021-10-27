package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.services.DataGouvFrDataSetTools;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.Association.CSV_HEADER_ASSOCIATION;

@ApplicationScoped
public class AssociationReader implements RecordReader<CSVRecord> {

    private static final Logger LOGGER = Logger.getLogger(AssociationReader.class);
    private static final String DATASET_NAME = "repertoire-national-des-associations";
    private static final String DATASET_URL = "https://media.interieur.gouv.fr/rna/rna_import_20211001.zip";

    private final AtomicLong recordNumber = new AtomicLong(0L);

    @Inject
    HttpDataQuerier httpDataQuerier;

    @Inject
    DataGouvFrDataSetTools dataGouvFrDataSetTools;

    private ZipArchiveInputStream archiveInputStream;
    private Iterator<CSVRecord> iterator = Collections.emptyIterator();

    @Override
    public void open() throws Exception {

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
    public void close() throws Exception {
        archiveInputStream.close();
    }

    @Override
    public Record<CSVRecord> readRecord() {

        return nextItem()
                .map(this::toRecord)
                .orElse(null);
    }

    private Record<CSVRecord> toRecord(CSVRecord item) {
        return new GenericRecord<>(generateHeader(recordNumber.getAndIncrement()), item);
    }

    private Header generateHeader(@Nullable Long recordNumber) {

        return new Header(
                recordNumber,
                DATASET_URL,
                LocalDateTime.now()
        );
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
                .setHeader(CSV_HEADER_ASSOCIATION)
                .setSkipHeaderRecord(true)
                .setDelimiter(";")
                .setRecordSeparator("\n")
                .setNullString("")
                .build();
    }
}
