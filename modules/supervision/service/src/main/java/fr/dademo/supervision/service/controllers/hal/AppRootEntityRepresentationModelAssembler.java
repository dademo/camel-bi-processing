/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import jakarta.annotation.Nonnull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * @author dademo
 */
public interface AppRootEntityRepresentationModelAssembler<T> extends RepresentationModelAssembler<T, EntityModel<T>> {

    @Nonnull
    Link[] getLinks(@Nonnull Long entityId);
}
