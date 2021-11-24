/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company_legal_history.writers;

import fr.dademo.bi.companies.jobs.stg.company_legal_history.CompanyLegalHistoryItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration
public class DefaultCompanyLegalHistoryItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyLegalHistoryItemWriter.class)
    public CompanyLegalHistoryItemWriter defaultCompanyLegalHistoryItemWriterBean() {
        return new CompanyLegalHistoryNoActionItemWriterImpl();
    }
}
