package fr.dademo.bi.companies.jobs.stg.association_waldec.writers;

import fr.dademo.bi.companies.jobs.stg.association_waldec.AssociationWaldecItemWriter;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.integration.annotation.Default;
import org.springframework.stereotype.Component;

@Component
@Default
public class NoActionAssociationWaldecItemWriterImpl extends NoActionItemWriter<AssociationWaldec> implements AssociationWaldecItemWriter {
}
