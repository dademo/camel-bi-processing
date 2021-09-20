package fr.dademo.bi.companies.jobs.stg.naf;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.jobs.stg.naf.entities.NafDefinitionContainer;
import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemReader;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@Dependent
@Named(NafReader.BEAN_NAME)
public class NafReader implements ItemReader {

    public static final String BEAN_NAME = "NafReader";
    private static final Logger LOGGER = Logger.getLogger(NafReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String DATASET_URL_QUERY_PARAMETERS = "format=json";

    @Inject
    HttpDataQuerier httpDataQuerier;

    private Iterator<NafDefinitionContainer> iterator;

    //@SneakyThrows
    @Override
    public void open(Serializable checkpoint) throws Exception {

        LOGGER.info("Reading values");
        // Querying for values
        var queryUrl = new URL(DATASET_URL + "?" + DATASET_URL_QUERY_PARAMETERS);

        iterator = MAPPER.<List<NafDefinitionContainer>>readValue(
                httpDataQuerier.basicQuery(queryUrl),
                MAPPER
                        .getTypeFactory()
                        .constructCollectionType(List.class, NafDefinitionContainer.class)
        ).iterator();
    }

    @Override
    public void close() throws Exception {
        // Nothing
    }

    @Override
    public Object readItem() throws Exception {

        return iterator.hasNext() ?
                iterator.next() :
                null;
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return null;
    }

    @SneakyThrows
    private void consumeResultStream(InputStream inputStream) {

        iterator = MAPPER.<List<NafDefinitionContainer>>readValue(
                inputStream,
                MAPPER
                        .getTypeFactory()
                        .constructCollectionType(List.class, NafDefinitionContainer.class)
        ).iterator();
    }
}
