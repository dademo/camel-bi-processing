/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association_waldec.writers;

import fr.dademo.bi.companies.jobs.stg.association_waldec.AssociationWaldecItemWriter;
import fr.dademo.bi.companies.jobs.stg.association_waldec.datamodel.AssociationWaldec;
import fr.dademo.bi.companies.tools.batch.writer.NoActionItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.bi.companies.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association_waldec.JobDefinition.ASSOCIATION_WALDEC_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + ASSOCIATION_WALDEC_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_NO_ACTION_TYPE
)
public class AssociationWaldecNoActionItemWriterImpl extends NoActionItemWriter<AssociationWaldec> implements AssociationWaldecItemWriter {
}
