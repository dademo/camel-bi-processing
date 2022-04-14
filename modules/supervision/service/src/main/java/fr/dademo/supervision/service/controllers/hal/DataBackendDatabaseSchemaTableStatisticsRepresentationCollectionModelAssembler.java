/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaTableStatisticsDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaTableStatisticsRepresentationCollectionModelAssembler extends AbstractDataBackendDatabaseSchemaTableStatisticsRepresentationModelAssembler {

    private final Long tableId;
    private final Date from;
    private final Date to;

    @Nonnull
    public CollectionModel<DataBackendDatabaseSchemaTableStatisticsDto> toCollectionModel(@Nonnull Iterable<DataBackendDatabaseSchemaTableStatisticsDto> entities) {

        return CollectionModel.of(
            entities,
            getLinks()
        );
    }
}
