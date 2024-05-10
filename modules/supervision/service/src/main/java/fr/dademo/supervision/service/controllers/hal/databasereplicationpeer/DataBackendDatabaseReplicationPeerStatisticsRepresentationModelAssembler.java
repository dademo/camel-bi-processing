/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databasereplicationpeer;

import fr.dademo.supervision.service.controllers.DataBackendDatabaseReplicationPeerController;
import fr.dademo.supervision.service.controllers.hal.AbstractDataBackendStatisticsRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerStatisticsDto;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.Date;

/**
 * @author dademo
 */
@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public final class DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler
    extends AbstractDataBackendStatisticsRepresentationModelAssembler<DataBackendDatabaseReplicationPeerStatisticsDto> {

    private final Long replicationPeerId;
    private final Date from;
    private final Date to;

    public static DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler ofTableWithDefault(@Nonnull Long databaseId) {

        return new DataBackendDatabaseReplicationPeerStatisticsRepresentationModelAssembler(
            databaseId, DefaultHalValues.getDefaultFrom(), DefaultHalValues.getDefaultTo()
        );
    }

    @Nonnull
    public Link[] getLinks() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findDataBackendDatabaseReplicationPeerStatisticsById(
                    getParentId(),
                    getFrom(),
                    getTo()
                )
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findLatestDataBackendDatabaseReplicationPeerStatisticById(
                    getParentId()
                )
            ).withRel("latest-replication-peer"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findDataBackendDatabaseReplicationPeerById(getParentId())
            ).withRel("replication-peer"),
        };
    }

    @Override
    protected Long getParentId() {
        return replicationPeerId;
    }
}