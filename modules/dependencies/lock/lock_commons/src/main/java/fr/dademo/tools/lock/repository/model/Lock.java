/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.lock.repository.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

public interface Lock extends AutoCloseable {

    @Nonnull
    @NotBlank
    String getDescription();
}
