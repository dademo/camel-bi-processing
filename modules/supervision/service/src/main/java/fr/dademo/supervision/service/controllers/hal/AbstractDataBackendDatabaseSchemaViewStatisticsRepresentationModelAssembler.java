/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaTableController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.Date;

/**
 * @author dademo
 */
public abstract class AbstractDataBackendDatabaseSchemaViewStatisticsRepresentationModelAssembler {

    protected abstract Long getViewId();

    protected abstract Date getFrom();

    protected abstract Date getTo();


    protected Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    getViewId(),
                    getFrom(),
                    getTo()
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findLatestDataBackendDatabaseSchemaTableStatisticById(
                    getViewId()
                )
            ).withRel("latest-view-statistic"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(getViewId())
            ).withRel("view"),
        };
    }
}
