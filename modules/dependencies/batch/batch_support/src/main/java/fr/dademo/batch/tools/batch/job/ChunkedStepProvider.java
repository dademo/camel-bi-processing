/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import fr.dademo.batch.configuration.BatchConfiguration;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Step;

import java.util.List;

/**
 * @author dademo
 */
public interface ChunkedStepProvider {

    @Nonnull
    Step getStep(@NotBlank String jobName,
                 @Nonnull BatchConfiguration.JobConfiguration jobConfiguration,
                 @Nonnull List<ChunkListener> chunkListeners);
}
