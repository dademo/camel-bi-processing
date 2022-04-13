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

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaViewController;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseSchemaViewRepresentationModelAssembler implements RepresentationModelAssembler<DataBackendDatabaseSchemaViewDto, EntityModel<DataBackendDatabaseSchemaViewDto>> {

    public static final DataBackendDatabaseSchemaViewRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaViewRepresentationModelAssembler();
    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaViewDto> toModel(@Nonnull DataBackendDatabaseSchemaViewDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseSchemaId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaViewId, @Nonnull Long databaseSchemaId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseSchemaViewId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaController.class).findDatabaseSchemaViewsForDatabase(databaseSchemaId, Pageable.unpaged())
            ).withRel("views"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewStatisticsById(
                    databaseSchemaViewId,
                    Date.from(LocalDateTime.now().minus(DEFAULT_TEMPORAL_AMOUNT).toInstant(ZoneOffset.UTC)),
                    new Date()
                )
            ).withRel("view_statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaViewController.class).findDataBackendDatabaseSchemaViewById(databaseSchemaId)
            ).withRel("schema"),
        };
    }
}