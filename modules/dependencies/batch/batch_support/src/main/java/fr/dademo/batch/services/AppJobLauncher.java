/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

/**
 * @author dademo
 */
public interface AppJobLauncher {

    /**
     * Return <i>true</i> in case all batches completed successfully.
     *
     * @return if all batch completed successfully
     */
    boolean runAll();
}
