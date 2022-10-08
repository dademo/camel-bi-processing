/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.batch.tools.batch.reader.StgJobItemReader;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author dademo
 */
@Slf4j
@Component
public class NafDefinitionItemReader extends StgJobItemReader<NafDefinitionContainer> {

    private final DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private final ObjectMapper objectMapper;

    private Iterator<NafDefinitionContainer> iterator = Collections.emptyIterator();

    public NafDefinitionItemReader(DataGouvFrDataQuerierService dataGouvFrDataQuerierService,
                                   ObjectMapper objectMapper) {
        this.dataGouvFrDataQuerierService = dataGouvFrDataQuerierService;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public void open(@Nonnull ExecutionContext executionContext) {

        log.info("Reading values");
        iterator = objectMapper.<List<NafDefinitionContainer>>readValue(
            dataGouvFrDataQuerierService.queryForStream(
                (DataGouvFrDataSetResource) getDataSetResource()
            ),
            objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, NafDefinitionContainer.class)
        ).iterator();
    }

    @Override
    public void close() throws ItemStreamException {
        // Nothing to do
    }

    @Override
    public NafDefinitionContainer read() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
