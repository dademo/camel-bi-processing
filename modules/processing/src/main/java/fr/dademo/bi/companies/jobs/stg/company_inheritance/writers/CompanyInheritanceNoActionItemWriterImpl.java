/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_inheritance.writers;

import fr.dademo.batch.tools.batch.writer.NoActionItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.CompanyInheritanceItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_inheritance.datamodel.CompanyInheritance;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_inheritance.JobDefinition.COMPANY_INHERITANCE_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_INHERITANCE_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_NO_ACTION_TYPE
)
public class CompanyInheritanceNoActionItemWriterImpl extends NoActionItemWriter<CompanyInheritance> implements CompanyInheritanceItemWriter {
}
