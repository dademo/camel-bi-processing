/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchSharedValues {

    public static final String KEY_BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION = "BATCH_EXECUTION_CONTEXT_DATASET_DEFINITION";
    public static final String KEY_BATCH_EXECUTION_CONTEXT_DATASET_DTO = "BATCH_EXECUTION_CONTEXT_DATASET_DTO";
}
