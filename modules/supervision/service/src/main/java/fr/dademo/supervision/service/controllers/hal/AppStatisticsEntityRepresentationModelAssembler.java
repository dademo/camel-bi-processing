/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
public interface AppStatisticsEntityRepresentationModelAssembler<T> {

    @Nonnull
    default EntityModel<T> toModel(@Nonnull T entity) {
        return EntityModel.of(entity, getLinks());
    }

    @Nonnull
    default CollectionModel<T> toCollectionModel(@Nonnull Iterable<T> entities) {
        return CollectionModel.of(entities, getLinks());
    }

    @Nonnull
    Link[] getLinks();
}
