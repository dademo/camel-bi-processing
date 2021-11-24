/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.company.writers;

import fr.dademo.bi.companies.jobs.stg.company.CompanyItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration
public class DefaultCompanyItemWriterBean {

    @Bean
    @ConditionalOnMissingBean(CompanyItemWriter.class)
    public CompanyItemWriter defaultCompanyItemWriterBean() {
        return new CompanyNoActionItemWriterImpl();
    }
}
