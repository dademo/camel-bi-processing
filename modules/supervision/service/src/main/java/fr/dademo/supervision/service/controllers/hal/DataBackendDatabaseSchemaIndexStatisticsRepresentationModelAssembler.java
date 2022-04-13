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

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaIndexController;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaIndexStatisticsDto;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;
import java.util.Date;

@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaIndexStatisticsRepresentationModelAssembler {

    private final Long indexId;
    private final Date from;
    private final Date to;

    @Nonnull
    public CollectionModel<DataBackendDatabaseSchemaIndexStatisticsDto> toCollectionModel(@Nonnull Iterable<DataBackendDatabaseSchemaIndexStatisticsDto> entities) {

        return CollectionModel.of(
            entities,
            getLinks()
        );
    }

    private Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexStatisticsById(
                    indexId,
                    from,
                    to
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaIndexController.class).findDataBackendDatabaseSchemaIndexById(indexId)
            ).withRel("index"),
        };
    }
}