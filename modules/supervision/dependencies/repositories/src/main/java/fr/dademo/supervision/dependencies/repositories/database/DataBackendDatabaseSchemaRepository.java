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

package fr.dademo.supervision.dependencies.repositories.database;

import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.annotation.Nonnull;
import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DataBackendDatabaseSchemaRepository extends
    JpaRepository<DataBackendDatabaseSchemaEntity, Long> {

    @Nonnull
    @Override
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    <S extends DataBackendDatabaseSchemaEntity> Optional<S> findOne(@Nonnull Example<S> example);

    @Nonnull
    @Override
    <S extends DataBackendDatabaseSchemaEntity> S save(@Nonnull S entity);
}
