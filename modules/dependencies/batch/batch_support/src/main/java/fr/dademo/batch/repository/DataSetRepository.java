/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.repository;

import fr.dademo.batch.repository.datamodel.DataSetEntity;

import javax.annotation.Nonnull;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

public interface DataSetRepository {

    boolean existsById(@Nonnull @NotBlank String id);

    Optional<DataSetEntity> findById(@Nonnull @NotBlank String id);

    DataSetEntity save(DataSetEntity entity);

    void delete(DataSetEntity entity);

    Optional<DataSetEntity> findFirstByNameOrderByTimestampDesc(@Nonnull @NotBlank String name);

    List<DataSetEntity> findByName(@Nonnull @NotBlank String name);

    Optional<DataSetEntity> findFirstByNameAndParentOrderByTimestampDesc(@Nonnull @NotBlank String name, @Nonnull @NotBlank String parent);

    Optional<DataSetEntity> findFirstByNameAndSourceAndSourceSubOrderByTimestampDesc(@Nonnull @NotBlank String name, String source, String sourceSub);
}
