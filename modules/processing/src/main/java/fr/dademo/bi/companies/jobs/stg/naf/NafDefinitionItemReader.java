package fr.dademo.bi.companies.jobs.stg.naf;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.tools.batch.reader.HttpItemStreamReaderSupport;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.helpers.DataGouvFrFilterHelpers;
import fr.dademo.data.helpers.data_gouv_fr.repository.DataGouvFrDataQuerierService;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Component
public class NafDefinitionItemReader extends HttpItemStreamReaderSupport<NafDefinitionContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NafDefinitionItemReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATASET_TITLE = "nomenclature-dactivites-francaise-naf-rev-2-code-ape";
    private static final String DATASET_RESOURCE_TITLE = "Export au format JSON";

    @Autowired
    private DataGouvFrDataQuerierService dataGouvFrDataQuerierService;

    private Iterator<NafDefinitionContainer> iterator;

    @SneakyThrows
    @Override
    public void open(@Nonnull ExecutionContext executionContext) {

        LOGGER.info("Getting dataset definition");
        final var dataGouvFrDataSet = dataGouvFrDataQuerierService.getDataSet(DATASET_TITLE);
        final var dataGouvFrDataSetResource = dataGouvFrDataSet
                .getResources().stream()
                .filter(DataGouvFrFilterHelpers.fieldEquals(DataGouvFrDataSetResource::getTitle, DATASET_RESOURCE_TITLE))
                .max(Comparator.comparing(DataGouvFrDataSetResource::dateTimeKeyExtractor))
                .orElseThrow(() -> new ResourceNotFoundException(DATASET_RESOURCE_TITLE, dataGouvFrDataSet));

        LOGGER.info("Reading values");
        iterator = MAPPER.<List<NafDefinitionContainer>>readValue(
                dataGouvFrDataQuerierService.queryForStream(dataGouvFrDataSetResource),
                MAPPER
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
