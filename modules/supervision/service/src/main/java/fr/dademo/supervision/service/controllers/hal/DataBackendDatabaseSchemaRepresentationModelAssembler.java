/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import fr.dademo.supervision.service.controllers.*;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseSchemaRepresentationModelAssembler implements RepresentationModelAssembler<DataBackendDatabaseSchemaDto, EntityModel<DataBackendDatabaseSchemaDto>> {

    public static final DataBackendDatabaseSchemaRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaDto> toModel(@Nonnull DataBackendDatabaseSchemaDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaId, @Nonnull Long databaseId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDataBackendDatabaseSchemaById(databaseSchemaId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDatabaseSchemasForDatabase(databaseId, Pageable.unpaged())
            ).withRel("database-schemas"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(databaseId)
            ).withRel("tables"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseId)
            ).withRel("views"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseId)
            ).withRel("indexes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseController.class).findDataBackendDatabaseById(databaseId)
            ).withRel("database"),
        };
    }
}