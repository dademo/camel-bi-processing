package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionItemWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.naf.JobDefinition.NAF_CONFIG_JOB_NAME;

@Component
@ConditionalOnProperty(
        value = CONFIG_JOBS_BASE + "." + NAF_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
        havingValue = CONFIG_MONGODB_TYPE
)
public class NafMongoDBDefinitionItemWriterImpl implements NafDefinitionItemWriter, ItemStreamWriter<NafDefinition> {

    public static final String COLLECTION_NAME = "naf";
    private static final Logger LOGGER = LoggerFactory.getLogger(NafMongoDBDefinitionItemWriterImpl.class);

    @Autowired
    @Qualifier(STG_MONGO_TEMPLATE_CONFIG_BEAN_NAME)
    @Getter
    private MongoTemplate mongoTemplate;

    @SneakyThrows
    @Override
    public void write(List<? extends NafDefinition> items) {

        LOGGER.info("Writing {} naf definition documents", items.size());
        final var result = mongoTemplate.getCollection(COLLECTION_NAME)
                .withDocumentClass(NafDefinition.class)
                .insertMany(items);
        LOGGER.info("{} items added", result.getInsertedIds().size());
    }

    @Override
    public void open(@Nonnull ExecutionContext executionContext) throws ItemStreamException {

        // We clean the target collection
        LOGGER.info("Cleaning collection `{}`", COLLECTION_NAME);
        mongoTemplate.dropCollection(COLLECTION_NAME);
        LOGGER.info("Done");
    }

    @Override
    public void update(@Nonnull ExecutionContext executionContext) throws ItemStreamException {
        // Nothing
    }

    @Override
    public void close() throws ItemStreamException {
        // Nothing
    }
}
