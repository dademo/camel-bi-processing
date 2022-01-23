/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author dademo
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "fr.dademo.supervision")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
