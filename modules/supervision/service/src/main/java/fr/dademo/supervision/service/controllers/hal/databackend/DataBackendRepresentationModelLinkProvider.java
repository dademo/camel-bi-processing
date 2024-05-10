/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databackend;

import fr.dademo.supervision.service.controllers.DataBackendController;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.function.Supplier;

/**
 * @author dademo
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE, staticName = "usingDataBackendId")
final class DataBackendRepresentationModelLinkProvider implements Supplier<Link[]> {

    @Nonnull
    private final Long dataBackendId;

    @Override
    public Link[] get() {

        return new Link[]{
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendById(dataBackendId)
            ).withSelfRel(),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackends(Pageable.unpaged())
            ).withRel("data-backends"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findClusterNodesForDataBackend(dataBackendId, DataBackendController.DEFAULT_EXECUTIONS_SIZE)
            ).withRel("data-backend-nodes"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDatabasesForDataBackend(dataBackendId, Pageable.unpaged())
            ).withRel("databases"),
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(DataBackendController.class).findDataBackendDatabaseReplicationPeersById(dataBackendId, Pageable.unpaged())
            ).withRel("database-replication"),
        };
    }
}
