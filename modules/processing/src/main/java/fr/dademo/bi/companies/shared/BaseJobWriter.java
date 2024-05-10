/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies.shared;

import fr.dademo.batch.configuration.BatchConfiguration;
import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseJobWriter {

    protected static Optional<String> getJobInputDataSourceName(@Nonnull String configJobName,
                                                                @Nonnull BatchConfiguration batchConfiguration) {

        return Optional
            .ofNullable(batchConfiguration.getJobConfigurationByName(configJobName).getInputDataSource())
            .map(BatchConfiguration.JobDataSourceConfiguration::getName);
    }

    protected static Optional<String> getJobOutputDataSourceName(@Nonnull String configJobName,
                                                                 @Nonnull BatchConfiguration batchConfiguration) {

        return Optional
            .ofNullable(batchConfiguration.getJobConfigurationByName(configJobName).getOutputDataSource())
            .map(BatchConfiguration.JobDataSourceConfiguration::getName);
    }
}
