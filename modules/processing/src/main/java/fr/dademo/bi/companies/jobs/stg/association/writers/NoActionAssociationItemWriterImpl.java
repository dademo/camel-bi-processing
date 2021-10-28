package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionAssociationItemWriterImpl extends NoActionItemWriter<Association> implements AssociationItemWriter {
}
