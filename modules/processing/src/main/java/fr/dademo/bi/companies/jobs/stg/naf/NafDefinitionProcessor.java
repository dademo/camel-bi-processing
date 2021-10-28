package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class NafDefinitionProcessor implements ItemProcessor<NafDefinitionContainer, NafDefinition> {

    @Override
    public NafDefinition process(@Nonnull NafDefinitionContainer item) {
        return item.getFields();
    }
}
