/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.database;

import fr.dademo.supervision.service.controllers.DataBackendController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseController;
import fr.dademo.supervision.service.controllers.hal.AppLinkedEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseDto;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseRepresentationModelAssembler
    implements AppLinkedEntityRepresentationModelAssembler<DataBackendDatabaseDto> {

    public static final DataBackendDatabaseRepresentationModelAssembler INSTANCE = new DataBackendDatabaseRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseDto> toModel(@Nonnull DataBackendDatabaseDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDescriptionId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long databaseId, @Nonnull Long backendDescriptionId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseById(databaseId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDatabasesForDataBackend(backendDescriptionId, Pageable.unpaged())
            ).withRel("databases"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(backendDescriptionId)
            ).withRel("data-backend"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDatabaseSchemasForDatabase(databaseId, Pageable.unpaged())
            ).withRel("schemas"),
        };
    }
}