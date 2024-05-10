/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company;

import fr.dademo.batch.resources.ResourcesReaderWrapperProvider;
import fr.dademo.batch.resources.WrappedRowResource;
import fr.dademo.batch.tools.batch.reader.StgJobItemReader;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import jakarta.annotation.Nonnull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.csv.CSVFormat;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

import static fr.dademo.bi.companies.jobs.stg.company.datamodel.Company.CSV_HEADER_COMPANY;

/**
 * @author dademo
 */
@Slf4j
@Component
public class CompanyItemReader extends StgJobItemReader<WrappedRowResource> {

    private final DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private ZipArchiveInputStream archiveInputStream = null;
    private Iterator<WrappedRowResource> iterator = Collections.emptyIterator();

    public CompanyItemReader(DataGouvFrDataQuerierService dataGouvFrDataQuerierService) {
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
    }

    @SneakyThrows
    public void open(@Nonnull ExecutionContext executionContext) {

        log.info("Reading values");
        archiveInputStream = new ZipArchiveInputStream(dataGouvFrDataQuerierService.queryForStream(
            (DataGouvFrDataSetResource) getDataSetResource()
        ));
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
            .setHeader(CSV_HEADER_COMPANY)
            .setSkipHeaderRecord(true)
            .setDelimiter(",")
            .setRecordSeparator("\n")
            .setNullString("")
            .build();
    }
}
