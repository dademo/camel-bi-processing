/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author dademo
 */
public interface AppJobLauncher {

    /**
     * Runs the configured jobs (all if none provided by command-line).
     * Return <i>true</i> in case all batches completed successfully.
     *
     * @param onlyJobs the list of jobs to run (can be empty, meaning all jobs should run)
     * @param force    whether jobs must be forced (in case of a previous successful execution)
     * @return if all batch completed successfully
     */
    boolean run(@Nonnull List<String> onlyJobs, boolean force);

    /**
     * Get a list of all available jobs.
     *
     * @return a list of all available jobs
     */
    List<String> getAllAvailableJobs();
}
