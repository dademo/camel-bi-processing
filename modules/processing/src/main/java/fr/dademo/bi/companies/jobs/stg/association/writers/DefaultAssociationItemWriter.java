/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.association.writers;

import fr.dademo.bi.companies.jobs.stg.association.AssociationItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration
public class DefaultAssociationItemWriter {

    @Bean
    @ConditionalOnMissingBean(AssociationItemWriter.class)
    public AssociationItemWriter defaultAssociationItemWriterBean() {
        return new AssociationNoActionItemWriterImpl();
    }
}
