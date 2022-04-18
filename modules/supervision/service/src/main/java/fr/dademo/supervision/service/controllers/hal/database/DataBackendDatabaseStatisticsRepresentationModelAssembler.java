/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.database;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseController;
import fr.dademo.supervision.service.controllers.hal.AbstractDataBackendStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseStatisticsDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseStatisticsRepresentationModelAssembler
    extends AbstractDataBackendStatisticsRepresentationModelAssembler<DataBackendDatabaseStatisticsDto> {

    private final Long databaseId;
    private final Date from;
    private final Date to;

    public static DataBackendDatabaseStatisticsRepresentationModelAssembler ofDatabaseWithDefault(@Nonnull Long databaseId) {

        return new DataBackendDatabaseStatisticsRepresentationModelAssembler(
            databaseId, DefaultHalValues.getDefaultFrom(), DefaultHalValues.getDefaultTo()
        );
    }

    @Nonnull
    public Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseStatisticsById(
                    getParentId(),
                    getFrom(),
                    getTo()
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findLatestDataBackendDatabaseStatisticById(
                    getParentId()
                )
            ).withRel("latest-index-statistic"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseById(getParentId())
            ).withRel("database"),
        };
    }

    @Override
    protected Long getParentId() {
        return databaseId;
    }
}
