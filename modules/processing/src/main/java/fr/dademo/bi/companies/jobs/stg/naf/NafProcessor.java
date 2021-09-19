package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.entities.NafDefinitionContainer;
import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named(NafProcessor.BEAN_NAME)
public class NafProcessor implements ItemProcessor {

    public static final String BEAN_NAME = "NafProcessor";
    private static final Logger LOGGER = Logger.getLogger(NafProcessor.class);

    @Override
    public Object processItem(Object item) throws Exception {
        return ((NafDefinitionContainer) item).getFields();
    }
}
