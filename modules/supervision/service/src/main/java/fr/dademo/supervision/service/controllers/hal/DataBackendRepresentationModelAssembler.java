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

import fr.dademo.supervision.service.controllers.DataBackendController;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendRepresentationModelAssembler implements RepresentationModelAssembler<DataBackendDescriptionDto, EntityModel<DataBackendDescriptionDto>> {

    public static final DataBackendRepresentationModelAssembler INSTANCE = new DataBackendRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDescriptionDto> toModel(@Nonnull DataBackendDescriptionDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId())
        );
    }

    private Link[] getLinks(@Nonnull Long dataBackendId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(dataBackendId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackends(Pageable.unpaged())
            ).withRel("data-backends"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDatabasesForDataBackend(dataBackendId, Pageable.unpaged())
            ).withRel("databases"),
        };
    }
}
