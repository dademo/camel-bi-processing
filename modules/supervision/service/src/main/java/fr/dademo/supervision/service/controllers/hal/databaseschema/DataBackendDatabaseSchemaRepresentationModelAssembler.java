/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databaseschema;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaController;
import fr.dademo.supervision.service.controllers.hal.AppLinkedEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseSchemaRepresentationModelAssembler
    implements AppLinkedEntityRepresentationModelAssembler<DataBackendDatabaseSchemaDto> {

    public static final DataBackendDatabaseSchemaRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaDto> toModel(@Nonnull DataBackendDatabaseSchemaDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long databaseSchemaId, @Nonnull Long databaseId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDataBackendDatabaseSchemaById(databaseSchemaId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDatabaseSchemasForDatabase(databaseId, Pageable.unpaged())
            ).withRel("database-schemas"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaTablesForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaViewsForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("views"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaIndexesForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("indexes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseById(databaseId)
            ).withRel("database"),
        };
    }
}