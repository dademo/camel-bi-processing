package fr.dademo.bi.companies.jobs.stg.naf;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.services.DataGouvFrDataSetTools;
import fr.dademo.bi.companies.tools.batch.reader.HttpItemStreamReaderSupport;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class NafDefinitionItemReader extends HttpItemStreamReaderSupport<NafDefinitionContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NafDefinitionItemReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATASET_NAME = "nomenclature-dactivites-francaise-naf-rev-2-code-ape";
    private static final String DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String DATASET_URL_QUERY_PARAMETERS = "format=json";

    @Autowired
    private HttpDataQuerier httpDataQuerier;

    @Autowired
    private DataGouvFrDataSetTools dataGouvFrDataSetTools;

    private Iterator<NafDefinitionContainer> iterator;

    @SneakyThrows
    @Override
    public void open(@Nonnull ExecutionContext executionContext) {

        LOGGER.info("Reading values");

        final var queryUrl = new URL(DATASET_URL + "?" + DATASET_URL_QUERY_PARAMETERS);

        iterator = MAPPER.<List<NafDefinitionContainer>>readValue(
                httpDataQuerier.basicQuery(
                        queryUrl,
                        Stream.of(dataGouvFrDataSetTools.hashDefinitionOfDataSetResourceByUrl(DATASET_NAME, DATASET_URL, false))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList())
                ),
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
    public synchronized NafDefinitionContainer read() {
        return iterator.hasNext() ? iterator.next() : null;
    }
}
