/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.backends.model;

import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendDescription;
import fr.dademo.supervision.dependencies.backends.model.shared.DataBackendModuleMetaData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

/**
 * @author dademo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataBackendStateFetchServiceExecutionResult {

    @Nullable
    private DataBackendModuleMetaData moduleMetaData;

    @Nullable
    private DataBackendDescription dataBackendDescription;

}
