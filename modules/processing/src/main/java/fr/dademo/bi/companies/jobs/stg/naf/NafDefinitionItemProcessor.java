/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.jobs.stg.naf;

import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinition;
import fr.dademo.bi.companies.jobs.stg.naf.datamodel.NafDefinitionContainer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@Component
public class NafDefinitionItemProcessor implements ItemProcessor<NafDefinitionContainer, NafDefinition> {

    @Override
    public NafDefinition process(@Nonnull NafDefinitionContainer item) {
        return item.getFields();
    }
}
