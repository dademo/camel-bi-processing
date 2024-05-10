/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import jakarta.annotation.Nonnull;
import org.springframework.batch.core.Job;

/**
 * @author dademo
 */
public interface JobProvider {

    boolean isJobAvailable();

    @Nonnull
    String getJobName();

    @Nonnull
    Job getJob();
}
