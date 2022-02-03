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

package fr.dademo.supervision.dependencies.persistence.repositories.database;

import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import fr.dademo.supervision.dependencies.persistence.PersistenceBeans;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * @author dademo
 */
@CacheConfig(cacheNames = PersistenceBeans.CACHE_DATA_BACKEND_DATABASE_REPOSITORY, keyGenerator = PersistenceBeans.TASK_ENTITY_CLASS_KEY_GENERATOR_BEAN_NAME)
public interface DataBackendDatabaseRepository extends
    JpaRepository<DataBackendDatabaseEntity, Long>,
    JpaSpecificationExecutor<DataBackendDatabaseEntity> {

    @Nonnull
    @Override
    @Cacheable
    Optional<DataBackendDatabaseEntity> findOne(Specification<DataBackendDatabaseEntity> spec);

    @Nonnull
    @Override
    @CachePut
    <S extends DataBackendDatabaseEntity> S save(@Nonnull S entity);

    @Override
    @CacheEvict
    void delete(@Nonnull DataBackendDatabaseEntity entity);
}
