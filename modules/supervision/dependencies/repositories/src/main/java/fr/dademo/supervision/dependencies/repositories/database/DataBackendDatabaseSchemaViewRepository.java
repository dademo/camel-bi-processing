/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.repositories.database;

import fr.dademo.supervision.dependencies.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import jakarta.annotation.Nonnull;
import jakarta.persistence.QueryHint;
import org.hibernate.jpa.HibernateHints;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.Optional;

/**
 * @author dademo
 */
public interface DataBackendDatabaseSchemaViewRepository extends
    JpaRepository<DataBackendDatabaseSchemaViewEntity, Long> {

    @Nonnull
    @Override
    @QueryHints(@QueryHint(name = HibernateHints.HINT_CACHEABLE, value = "true"))
    <S extends DataBackendDatabaseSchemaViewEntity> Optional<S> findOne(@Nonnull Example<S> example);

    @Nonnull
    @Override
    <S extends DataBackendDatabaseSchemaViewEntity> S save(@Nonnull S entity);
}
