/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.file.beans;

import fr.dademo.reader.file.repository.DefaultFileDataQuerierRepository;
import fr.dademo.reader.file.repository.FileDataQuerierRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dademo
 */
@Configuration
public class DefaultFileReaderBeans {

    @Bean
    @ConditionalOnMissingBean(FileDataQuerierRepository.class)
    public FileDataQuerierRepository defaultFileDataQuerierRepository() {
        return new DefaultFileDataQuerierRepository();
    }
}
