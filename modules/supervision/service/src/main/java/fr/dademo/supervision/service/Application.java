/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@SpringBootApplication
@EnableTransactionManagement
@EntityScan(basePackages = "fr.dademo.supervision")
@EnableJpaRepositories
@OpenAPIDefinition(
    info = @Info(
        title = "Supervision restitution backend",
        description = "Supervision restitution backend",
        license = @License(name = "Mozilla Public License, version 2.0", url = "https://mozilla.org/MPL/2.0/"),
        version = "0.1"
    ),
    servers = {
        @Server(),
        @Server(url = "http://localhost:8080", description = "localhost, development"),
    }
)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @SuppressWarnings("unused")
    @Bean
    public WebMvcConfigurer corsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nonnull CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200");
            }
        };
    }
}
