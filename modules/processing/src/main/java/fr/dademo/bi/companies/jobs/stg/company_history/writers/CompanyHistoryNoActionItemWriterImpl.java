/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_history.writers;

import fr.dademo.batch.tools.batch.writer.NoActionItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_history.CompanyHistoryItemWriter;
import fr.dademo.bi.companies.jobs.stg.company_history.datamodel.CompanyHistory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static fr.dademo.batch.beans.BeanValues.*;
import static fr.dademo.bi.companies.jobs.stg.company_history.JobDefinition.COMPANY_HISTORY_CONFIG_JOB_NAME;

/**
 * @author dademo
 */
@Component
@ConditionalOnProperty(
    value = CONFIG_JOBS_BASE + "." + COMPANY_HISTORY_CONFIG_JOB_NAME + "." + CONFIG_JOB_OUTPUT_DATA_SOURCE + "." + CONFIG_WRITER_TYPE,
    havingValue = CONFIG_NO_ACTION_TYPE
)
public class CompanyHistoryNoActionItemWriterImpl extends NoActionItemWriter<CompanyHistory> implements CompanyHistoryItemWriter {
}
