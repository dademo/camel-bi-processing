package fr.dademo.bi.companies.jobs.stg.naf;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import fr.dademo.bi.companies.repositories.HttpDataQuerier;
import fr.dademo.bi.companies.services.DataGouvFrHashGetter;
import org.jboss.logging.Logger;
import org.jeasy.batch.core.reader.RecordReader;
import org.jeasy.batch.core.record.GenericRecord;
import org.jeasy.batch.core.record.Header;
import org.jeasy.batch.core.record.Record;

import javax.annotation.Nullable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class NafReader implements RecordReader<NafDefinitionContainer> {

    private static final Logger LOGGER = Logger.getLogger(NafReader.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String DATASET_NAME = "nomenclature-dactivites-francaise-naf-rev-2-code-ape";
    private static final String DATASET_URL = "https://data.iledefrance.fr/explore/dataset/nomenclature-dactivites-francaise-naf-rev-2-code-ape/download";
    private static final String DATASET_URL_QUERY_PARAMETERS = "format=json";

    private final AtomicLong recordNumber = new AtomicLong(0L);

    @Inject
    HttpDataQuerier httpDataQuerier;

    @Inject
    DataGouvFrHashGetter dataGouvFrHashGetter;

    private Iterator<NafDefinitionContainer> iterator;

    //@SneakyThrows
    @Override
    public void open() throws Exception {

        LOGGER.info("Reading values");
        // Querying for values
        final var queryUrl = new URL(DATASET_URL + "?" + DATASET_URL_QUERY_PARAMETERS);

        iterator = MAPPER.<List<NafDefinitionContainer>>readValue(
                httpDataQuerier.basicQuery(
                        queryUrl,
                        Stream.of(dataGouvFrHashGetter.hashDefinitionOfDataSetResourceByUrl(DATASET_NAME, DATASET_URL, false))
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
    public synchronized Record<NafDefinitionContainer> readRecord() {
        return iterator.hasNext() ? toRecord(iterator.next()) : null;
    }

    private Record<NafDefinitionContainer> toRecord(NafDefinitionContainer item) {
        return new GenericRecord<>(generateHeader(recordNumber.getAndIncrement()), item);
    }

    private Header generateHeader(@Nullable Long recordNumber) {

        return new Header(
                recordNumber,
                DATASET_URL + "?" + DATASET_URL_QUERY_PARAMETERS,
                LocalDateTime.now()
        );
    }
}
