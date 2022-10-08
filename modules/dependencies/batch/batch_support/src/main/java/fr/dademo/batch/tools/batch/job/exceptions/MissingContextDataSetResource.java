/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.exceptions;

import fr.dademo.batch.tools.batch.job.tasklets.DataSetResourceQueryTasklet;

/**
 * @author dademo
 */
public class MissingContextDataSetResource extends RuntimeException {

    private static final long serialVersionUID = 4769326201545858667L;

    public MissingContextDataSetResource() {
        super("Unable to locate the data set in the job context. Please run the " + DataSetResourceQueryTasklet.class.getName() + " task before this task.");
    }
}
