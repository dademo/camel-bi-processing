/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.job.persistence_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author dademo
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "fr.dademo.supervision")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
