/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databasereplicationpeer;

import fr.dademo.supervision.service.controllers.DataBackendController;
import fr.dademo.supervision.service.controllers.DataBackendDatabaseReplicationPeerController;
import fr.dademo.supervision.service.controllers.hal.AppLinkedEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.controllers.hal.DefaultHalValues;
import fr.dademo.supervision.service.services.dto.DataBackendDatabaseReplicationPeerDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import javax.annotation.Nonnull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendDatabaseReplicationPeerRepresentationModelAssembler
    implements AppLinkedEntityRepresentationModelAssembler<DataBackendDatabaseReplicationPeerDto> {

    public static final DataBackendDatabaseReplicationPeerRepresentationModelAssembler INSTANCE = new DataBackendDatabaseReplicationPeerRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDatabaseReplicationPeerDto> toModel(@Nonnull DataBackendDatabaseReplicationPeerDto entity) {

        return EntityModel.of(
            entity,
            getLinks(entity.getId(), entity.getDataBackendDatabaseId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long replicationPeerId, @Nonnull Long backendDescriptionId) {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findDataBackendDatabaseReplicationPeerById(replicationPeerId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendDatabaseReplicationPeersById(backendDescriptionId, Pageable.unpaged())
            ).withRel("replication-peers"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findDataBackendDatabaseReplicationPeerStatisticsById(
                    replicationPeerId,
                    DefaultHalValues.getDefaultFrom(),
                    DefaultHalValues.getDefaultTo()
                )
            ).withRel("replication-peer-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendDatabaseReplicationPeerController.class).findLatestDataBackendDatabaseReplicationPeerStatisticById(
                    replicationPeerId
                )
            ).withRel("latest-replication-peer-statistics"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(backendDescriptionId)
            ).withRel("data-backend"),
        };
    }
}