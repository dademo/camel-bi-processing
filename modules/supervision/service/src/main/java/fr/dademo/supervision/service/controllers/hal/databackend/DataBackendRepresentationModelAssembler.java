/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databackend;

import fr.dademo.supervision.service.controllers.hal.AppRootEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.services.dto.DataBackendDescriptionDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DataBackendRepresentationModelAssembler
    implements AppRootEntityRepresentationModelAssembler<DataBackendDescriptionDto> {

    public static final DataBackendRepresentationModelAssembler INSTANCE = new DataBackendRepresentationModelAssembler();

    @Nonnull
    @Override
    public EntityModel<DataBackendDescriptionDto> toModel(@Nonnull DataBackendDescriptionDto entity) {
        return EntityModel.of(
            entity,
            getLinks(entity.getId())
        );
    }

    @Nonnull
    public Link[] getLinks(@Nonnull Long dataBackendId) {
        return DataBackendRepresentationModelLinkProvider.usingDataBackendId(dataBackendId).get();
    }
}
