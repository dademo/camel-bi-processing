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

import fr.dademo.supervision.service.services.dto.DataBackendDatabaseSchemaViewStatisticsDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseSchemaViewStatisticsRepresentationModelAssembler
    extends AbstractDataBackendDatabaseSchemaViewStatisticsRepresentationModelAssembler
    implements RepresentationModelAssembler<DataBackendDatabaseSchemaViewStatisticsDto, EntityModel<DataBackendDatabaseSchemaViewStatisticsDto>> {

    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    private final Long viewId;

    @Nonnull
    public EntityModel<DataBackendDatabaseSchemaViewStatisticsDto> toModel(@Nonnull DataBackendDatabaseSchemaViewStatisticsDto entity) {

        return EntityModel.of(
            entity,
            getLinks()
        );
    }

    @Override
    protected Date getFrom() {
        return Date.from(Instant.now().minus(DEFAULT_TEMPORAL_AMOUNT));
    }

    @Override
    protected Date getTo() {
        return new Date();
    }
}