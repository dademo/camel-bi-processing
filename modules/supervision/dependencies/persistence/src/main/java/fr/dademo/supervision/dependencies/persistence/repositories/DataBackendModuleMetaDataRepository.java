/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.repositories;

import fr.dademo.supervision.dependencies.entities.DataBackendModuleMetaDataEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.annotation.Nonnull;
import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DataBackendModuleMetaDataRepository extends
    JpaRepository<DataBackendModuleMetaDataEntity, Long> {

    @Nonnull
    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    <S extends DataBackendModuleMetaDataEntity> Optional<S> findOne(@Nonnull Example<S> example);

    @Nonnull
    @Override
    <S extends DataBackendModuleMetaDataEntity> S save(@Nonnull S entity);
}
