/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.specialSpecifications;

import fr.dademo.supervision.dependencies.entities.database.database.DataBackendDatabaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Nonnull;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author dademo
 */
@AllArgsConstructor(staticName = "forEntity")
public class DataBackendDatabaseEntitySpecification implements Specification<DataBackendDatabaseEntity> {

    private static final long serialVersionUID = -2747709588535048910L;

    private static final String FIELD_NAME = "name";
    private static final String FIELD_BACKEND_DESCRIPTION = "backendDescription";

    private final DataBackendDatabaseEntity comparedEntity;

    @Override
    public Predicate toPredicate(@Nonnull Root<DataBackendDatabaseEntity> root,
                                 @Nonnull CriteriaQuery<?> query,
                                 @Nonnull CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.and(
            criteriaBuilder.equal(root.get(FIELD_BACKEND_DESCRIPTION), comparedEntity.getBackendDescription()),
            namePredicate(root, criteriaBuilder)
        );
    }

    private Predicate namePredicate(Root<DataBackendDatabaseEntity> root, CriteriaBuilder criteriaBuilder) {

        return Optional.ofNullable(comparedEntity.getName())
            .map(entityName -> criteriaBuilder.equal(root.get(FIELD_NAME), entityName))
            .orElse(criteriaBuilder.isNull(root.get(FIELD_NAME)));
    }
}
