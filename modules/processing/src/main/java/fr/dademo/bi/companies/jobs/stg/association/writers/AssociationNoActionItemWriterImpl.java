/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.batch.tools.batch.writer.NoActionItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import fr.dademo.bi.companies.jobs.stg.association.datamodel.Association;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.association.JobDefinition.ASSOCIATION_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + ASSOCIATION_CONFIG_JOB_NAME + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_NO_ACTION_TYPE
)
public class AssociationNoActionItemWriterImpl extends NoActionItemWriter<Association> implements AssociationItemWriter {
}
