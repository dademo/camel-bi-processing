/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal.databackend;

import fr.dademo.supervision.service.controllers.hal.AppStatisticsEntityRepresentationModelAssembler;
import fr.dademo.supervision.service.services.dto.DataBackendClusterNodeDto;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;

import javax.annotation.Nonnull;

/**
 * @author dademo
 */
@AllArgsConstructor(staticName = "usingDataBackendId")
public final class DataBackendClusterNodeModelAssembler
    implements AppStatisticsEntityRepresentationModelAssembler<DataBackendClusterNodeDto> {

    @Nonnull
    private final Long dataBackendId;

    @Nonnull
    @Override
    public Link[] getLinks() {
        return DataBackendRepresentationModelLinkProvider.usingDataBackendId(dataBackendId).get();
    }
}
