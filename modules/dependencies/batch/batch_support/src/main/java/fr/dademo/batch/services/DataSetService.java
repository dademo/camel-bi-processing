/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.services;

import fr.dademo.batch.services.dto.DataSetDto;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface DataSetService {

    Optional<DataSetDto> findById(@Nonnull @NotBlank String id);

    List<DataSetDto> getDataSetByName(@Nonnull @NotBlank String name);

    DataSetDto createDataSet(@Nonnull @NotBlank String name, @Nonnull @NotBlank String source, @Nonnull @NotBlank String sourceSub);

    DataSetDto createDataSet(@Nonnull @NotBlank String name, @Nonnull @NotBlank String parentId);

    DataSetDto updateDataSet(@Nonnull DataSetDto dataSetDto);

    void deleteDataSet(@Nonnull DataSetDto dataSetDto);
}
