/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance;

import fr.dademo.batch.tools.batch.reader.UnidirectionalItemStreamReaderSupport;
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

import static fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance.CSV_HEADER_COMPANY_INHERITANCE;

/**
 * @author dademo
 */
@Component
public class CompanyInheritanceItemReader extends UnidirectionalItemStreamReaderSupport<CSVRecord> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyInheritanceItemReader.class);
    private static final String DATASET_TITLE = "base-sirene-des-entreprises-et-de-leurs-etablissements-siren-siret";
    private static final String DATA_TITLE_PREFIX = "Sirene : Fichier StockEtablissementLiensSuccession ";

    @Autowired
    private DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private Iterator<CSVRecord> iterator = Collections.emptyIterator();
    private ZipArchiveInputStream archiveInputStream;

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
        Optional.ofNullable(archiveInputStream).ifPresent(this::sneakyClose);
    }

    @Override
    public CSVRecord read() {
        return nextItem().orElse(null);
    }

    @SneakyThrows
    private synchronized Optional<CSVRecord> nextItem() {    // NOSONAR

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
