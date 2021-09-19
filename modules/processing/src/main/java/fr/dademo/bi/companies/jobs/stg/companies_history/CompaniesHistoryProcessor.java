package fr.dademo.bi.companies.jobs.stg.companies_history;

import org.jboss.logging.Logger;

import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

@Dependent
@Named(CompaniesHistoryProcessor.BEAN_NAME)
public class CompaniesHistoryProcessor implements ItemProcessor {

    public static final String BEAN_NAME = "CompaniesHistoryProcessor";
    private static final Logger LOGGER = Logger.getLogger(CompaniesHistoryProcessor.class);

    @Override
    public Object processItem(Object item) throws Exception {
        LOGGER.info("Processing item");
        return item;
    }
}
