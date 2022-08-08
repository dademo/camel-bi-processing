/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association;

import fr.dademo.batch.resources.ResourcesReaderWrapper;
import fr.dademo.batch.resources.ResourcesReaderWrapperProvider;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.reader.UnidirectionalItemStreamReaderSupport;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.association.datamodel.Association.CSV_HEADER_ASSOCIATION;

/**
 * @author dademo
 */
@Slf4j
@Component
@SuppressWarnings("java:S112")
public class AssociationItemReader extends UnidirectionalItemStreamReaderSupport<WrappedRowResource> {

    private static final String DATASET_TITLE = "repertoire-national-des-associations";
    private static final String DATA_TITLE_PREFIX = "Fichier Import ";

    @Autowired
    private DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private ZipArchiveInputStream archiveInputStream;
    private ResourcesReaderWrapper resourcesReaderWrapper;
    private Iterator<WrappedRowResource> iterator = Collections.emptyIterator();

    @SneakyThrows
    public void open(@Nonnull ExecutionContext executionContext) {

        log.info("Getting dataset definition");
        final var dataGouvFrDataSet = dataGouvFrDataQuerierService.getDataSet(DATASET_TITLE);
        final var dataGouvFrDataSetResource = dataGouvFrDataSet
            .getResources().stream()
            .filter(DataGouvFrFilterHelpers.fieldStartingWith(DataGouvFrDataSetResource::getTitle, DATA_TITLE_PREFIX))
            .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
            .orElseThrow(() -> new ResourceNotFoundException(DATA_TITLE_PREFIX + "*", dataGouvFrDataSet));

        log.info("Reading values");
        archiveInputStream = new ZipArchiveInputStream(dataGouvFrDataQuerierService.queryForStream(dataGouvFrDataSetResource));
    }

    @Override
    @SneakyThrows
    public void close() {
        Optional.ofNullable(archiveInputStream).ifPresent(this::sneakyClose);
    }

    @Override
    public WrappedRowResource read() {
        return nextItem().orElse(null);
    }

    @SneakyThrows
    private synchronized Optional<WrappedRowResource> nextItem() {   // NOSONAR

        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        } else {
            while (true) {
                ArchiveEntry archiveEntry;
                if ((archiveEntry = archiveInputStream.getNextEntry()) != null) {
                    if (!archiveEntry.isDirectory()) {
                        iterator = ResourcesReaderWrapperProvider.of(
                            csvFormat()
                                .parse(new InputStreamReader(archiveInputStream)),
                            false
                        ).iterator();
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
