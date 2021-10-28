package fr.dademo.bi.companies.jobs.stg.naf.writers;

import fr.dademo.bi.companies.jobs.stg.naf.NafDefinitionWriter;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jboss.logging.Logger;
import org.jooq.BatchBindStep;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionTable.NAF_DEFINITION;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.name;

@Component
@ConditionalOnProperty(
        value = CONFIG_DATASOURCE_JDBC + "." + STG_DATASOURCE_NAME + "." + CONFIG_ENABLED,
        havingValue = "true"
)
public class NafJdbcDefinitionWriterImpl implements NafDefinitionWriter {

    private static final Logger LOGGER = Logger.getLogger(NafJdbcDefinitionWriterImpl.class);

    @Autowired
    @Qualifier(STG_DSL_CONTEXT)
    @Getter
    private DSLContext dslContext;

    @SneakyThrows
    @Override
    public void write(List<? extends NafDefinition> items) {

        LOGGER.info(String.format("Writing %d naf definition documents", items.size()));

        final var batchInsertStatement = dslContext.batch(dslContext.insertInto(NAF_DEFINITION,
                        NAF_DEFINITION.FIELD_NAF_CODE,
                        NAF_DEFINITION.FIELD_TITLE,
                        NAF_DEFINITION.FIELD_TITLE_65,
                        NAF_DEFINITION.FIELD_TITLE_40
                ).values((String) null, null, null, null)
                .onConflict(NAF_DEFINITION.FIELD_NAF_CODE)
                .doUpdate()
                .set(NAF_DEFINITION.FIELD_TITLE, asExcludedField(NAF_DEFINITION.FIELD_TITLE))
                .set(NAF_DEFINITION.FIELD_TITLE_65, asExcludedField(NAF_DEFINITION.FIELD_TITLE_65))
                .set(NAF_DEFINITION.FIELD_TITLE_40, asExcludedField(NAF_DEFINITION.FIELD_TITLE_40)));

        items.stream()
                .map(this::nafDefinitionBind)
                .forEach(consumer -> consumer.accept(batchInsertStatement));

        final var batchResult = batchInsertStatement.execute();
        if (batchResult.length > 0) {
            final int totalUpdated = Arrays.stream(batchResult).sum();
            LOGGER.info(String.format("%d rows affected", totalUpdated));
        } else {
            LOGGER.error("An error occurred while running batch");
        }
    }

    private Consumer<BatchBindStep> nafDefinitionBind(NafDefinition nafDefinition) {

        return items -> items.bind(
                nafDefinition.getNafCode(),
                nafDefinition.getTitle(),
                nafDefinition.getTitle65(),
                nafDefinition.getTitle40()
        );
    }

    private <T> Field<T> asExcludedField(@Nonnull Field<T> field) {
        return field(name("excluded", field.getName()), field.getType());
    }
}
