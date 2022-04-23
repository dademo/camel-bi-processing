/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.services;

import fr.dademo.supervision.service.services.dto.DataBackendClusterNodeDto;

import javax.annotation.Nonnull;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

/**
 * @author dademo
 */
public interface DataBackendClusterNodeService {

    List<DataBackendClusterNodeDto> findDataBackendClusterNodeByDataBackendId(@Nonnull @Min(1) Long dataBackendId, @Nonnull @Min(0) Long size);

    List<DataBackendClusterNodeDto> findDataBackendClusterNodeByDataBackendIdBetween(
        @Nonnull @Min(1) Long id, @Nonnull Date from, @Nonnull Date to
    );

    List<DataBackendClusterNodeDto> findLatestDataBackendClusterNodesByDataBackendId(@Nonnull @Min(1) Long dataBackendId);
}
