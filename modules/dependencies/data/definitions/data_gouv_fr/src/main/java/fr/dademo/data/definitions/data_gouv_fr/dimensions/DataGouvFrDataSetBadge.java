/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.dimensions;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataGouvFrDataSetBadge {

    @Nonnull
    private String kind;
}
