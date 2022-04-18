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

package fr.dademo.supervision.dependencies.repositories;

import fr.dademo.supervision.dependencies.entities.DataBackendDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import javax.annotation.Nonnull;
import javax.persistence.QueryHint;
import java.util.Optional;

/**
 * @author dademo
 */
public interface DataBackendDescriptionRepository extends
    JpaRepository<DataBackendDescriptionEntity, Long> {

    @Nonnull
    @QueryHints(@QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true"))
    <S extends DataBackendDescriptionEntity> Optional<S> findOneByPrimaryUrl(String primaryUrl);

    @Nonnull
    @Override
    <S extends DataBackendDescriptionEntity> S save(@Nonnull S entity);
}
