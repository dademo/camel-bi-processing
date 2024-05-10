/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services.dto;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class DataSetDto {

    @Nonnull
    private String name;

    @Nullable
    private String parent;

    @Nullable
    private String source;

    @Nullable
    private String sourceSub;

    @Nonnull
    private DataSetDtoState state;

    public enum DataSetDtoState {
        READY,
        FAILED,
        RUNNING
    }
}
