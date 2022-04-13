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

import fr.dademo.supervision.service.controllers.DataBackendDatabaseSchemaTableController;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;
import java.util.Date;

@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaViewStatisticsRepresentationModelAssembler {

    private final Long viewId;
    private final Date from;
    private final Date to;

    @Nonnull
    public CollectionModel<DataBackendDatabaseSchemaViewStatisticsDto> toCollectionModel(@Nonnull Iterable<DataBackendDatabaseSchemaViewStatisticsDto> entities) {

        return CollectionModel.of(
            entities,
            getLinks()
        );
    }

    private Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableStatisticsById(
                    viewId,
                    from,
                    to
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseSchemaTableController.class).findDataBackendDatabaseSchemaTableById(viewId)
            ).withRel("view"),
        };
    }
}