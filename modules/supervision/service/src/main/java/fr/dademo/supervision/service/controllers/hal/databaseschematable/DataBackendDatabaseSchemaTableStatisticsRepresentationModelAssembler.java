/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databaseschematable;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaTableController;
import fr.dademo.supervision.service.controllers.hal.AbstractDataBackendStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaTableStatisticsRepresentationModelAssembler
    extends AbstractDataBackendStatisticsRepresentationModelAssembler<DataBackendDatabaseSchemaTableStatisticsDto> {

    private final Long tableId;
    private final Date from;
    private final Date to;

    public static DataBackendDatabaseSchemaTableStatisticsRepresentationModelAssembler ofTableWithDefault(@Nonnull Long tableId) {

        return new DataBackendDatabaseSchemaTableStatisticsRepresentationModelAssembler(
            tableId, DefaultHalValues.getDefaultFrom(), DefaultHalValues.getDefaultTo()
        );
    }

    @Nonnull
    public Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    getParentId(),
                    getFrom(),
                    getTo()
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaTablesForDatabase(getParentId(), Pageable.unpaged())
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    getParentId(),
                    DefaultHalValues.getDefaultFrom(),
                    DefaultHalValues.getDefaultTo()
                )
            ).withRel("table-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findLatestDataBackendDatabaseSchemaTableStatisticById(
                    getParentId()
                )
            ).withRel("latest-table-statistic"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(getParentId())
            ).withRel("table"),
        };
    }

    @Override
    protected Long getParentId() {
        return tableId;
    }
}