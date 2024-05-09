/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.repositories.database;

import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.QueryHints;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DataBackendDatabaseRepository extends
    JpaRepository<DataBackendDatabaseEntity, Long>,
    JpaSpecificationExecutor<DataBackendDatabaseEntity> {

    @Nonnull
    @Override
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    <S extends DataBackendDatabaseEntity> Optional<S> findOne(@Nonnull Example<S> example);

    @Nonnull
    @Override
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    Optional<DataBackendDatabaseEntity> findOne(Specification<DataBackendDatabaseEntity> spec);
}
