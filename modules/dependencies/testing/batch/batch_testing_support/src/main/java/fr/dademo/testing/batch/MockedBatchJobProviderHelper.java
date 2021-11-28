/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.testing.batch;

import fr.dademo.batch.tools.batch.job.BatchJobProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;

import static org.mockito.BDDMockito.given;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MockedBatchJobProviderHelper {

    public static BatchJobProvider mockedBatchJobProviderFor(Job job) {

        final var mockedJobProvider = Mockito.mock(BatchJobProvider.class);
        given(mockedJobProvider.getJob()).willReturn(job);
        return mockedJobProvider;
    }
}
