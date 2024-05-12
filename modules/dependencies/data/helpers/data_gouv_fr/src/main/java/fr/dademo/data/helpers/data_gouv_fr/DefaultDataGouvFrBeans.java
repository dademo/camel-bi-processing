/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr;

import fr.dademo.data.helpers.data_gouv_fr.cache.flow_ignore_checkers.DataGouvFrCacheFlowIgnoreChecker;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Configuration
public class DefaultDataGouvFrBeans {

    @SuppressWarnings("java:S1452")
    @Bean
    public CacheFlowIgnoreChecker dataGouvFrCacheFlowIgnoreChecker() {
        return new DataGouvFrCacheFlowIgnoreChecker();
    }
}
