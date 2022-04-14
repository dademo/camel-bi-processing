/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaIndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.Date;

/**
 * @author dademo
 */
public abstract class AbstractDataBackendDatabaseSchemaIndexStatisticsRepresentationModelAssembler {

    protected abstract Long getIndexId();

    protected abstract Date getFrom();

    protected abstract Date getTo();


    protected Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexStatisticsById(
                    getIndexId(),
                    getFrom(),
                    getTo()
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findLatestDataBackendDatabaseSchemaIndexStatisticsById(
                    getIndexId()
                )
            ).withRel("latest-index-statistic"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(getIndexId())
            ).withRel("index"),
        };
    }
}
