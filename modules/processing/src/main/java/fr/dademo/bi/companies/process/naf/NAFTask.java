package fr.dademo.bi.companies.process.naf;

import fr.dademo.bi.companies.AppTask;
import org.apache.camel.CamelContext;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NAFTask implements AppTask {

    private static final Logger LOGGER = Logger.getLogger(NAFTask.class);

    @Inject
    CamelContext context;

    @Override
    public void doRun() {

        LOGGER.info("Fetching data");
        context.getRoute("ApeTask").getRoute();
    }
}
