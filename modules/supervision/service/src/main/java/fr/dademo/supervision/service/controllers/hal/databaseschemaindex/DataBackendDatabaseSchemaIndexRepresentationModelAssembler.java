/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databaseschemaindex;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaIndexController;
import fr.dademo.supervision.service.controllers.hal.AppLinkedEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseSchemaIndexRepresentationModelAssembler
    implements AppLinkedEntityRepresentationModelAssembler<DataBackendDatabaseSchemaIndexDto> {

    public static final DataBackendDatabaseSchemaIndexRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaIndexRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaIndexDto> toModel(@Nonnull DataBackendDatabaseSchemaIndexDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseSchemaId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long databaseSchemaIndexId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseSchemaIndexId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaIndexesForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("indexes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexStatisticsById(
                    databaseSchemaIndexId,
                    DefaultHalValues.getDefaultFrom(),
                    DefaultHalValues.getDefaultTo()
                )
            ).withRel("index-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findLatestDataBackendDatabaseSchemaIndexStatisticsById(
                    databaseSchemaIndexId
                )
            ).withRel("latest-index-statistic"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseSchemaIndexId)
            ).withRel("schema"),
        };
    }
}