/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databaseschematable;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaTableController;
import fr.dademo.supervision.service.controllers.hal.AppLinkedEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableDto;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseSchemaTableRepresentationModelAssembler
    implements AppLinkedEntityRepresentationModelAssembler<DataBackendDatabaseSchemaTableDto> {

    public static final DataBackendDatabaseSchemaTableRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaTableRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaTableDto> toModel(@Nonnull DataBackendDatabaseSchemaTableDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseSchemaId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long databaseSchemaTableId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseSchemaTableId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaTablesForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    databaseSchemaTableId,
                    DefaultHalValues.getDefaultFrom(),
                    DefaultHalValues.getDefaultTo()
                )
            ).withRel("table-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findLatestDataBackendDatabaseSchemaTableStatisticById(
                    databaseSchemaTableId
                )
            ).withRel("latest-table-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseSchemaTableId)
            ).withRel("schema"),
        };
    }
}