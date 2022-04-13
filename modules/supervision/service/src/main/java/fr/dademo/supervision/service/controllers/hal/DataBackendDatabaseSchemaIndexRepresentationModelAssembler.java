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
import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaIndexController;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexDto;
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
public final class DataBackendDatabaseSchemaIndexRepresentationModelAssembler implements RepresentationModelAssembler<DataBackendDatabaseSchemaIndexDto, EntityModel<DataBackendDatabaseSchemaIndexDto>> {

    public static final DataBackendDatabaseSchemaIndexRepresentationModelAssembler INSTANCE = new DataBackendDatabaseSchemaIndexRepresentationModelAssembler();
    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseSchemaIndexDto> toModel(@Nonnull DataBackendDatabaseSchemaIndexDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseSchemaId())
        );
    }

    private Link[] getLinks(@Nonnull Long databaseSchemaIndexId, @Nonnull Long databaseSchemaId) {

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
                    Date.from(LocalDateTime.now().minus(DEFAULT_TEMPORAL_AMOUNT).toInstant(ZoneOffset.UTC)),
                    new Date()
                )
            ).withRel("index_statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(databaseSchemaId)
            ).withRel("schema"),
        };
    }
}