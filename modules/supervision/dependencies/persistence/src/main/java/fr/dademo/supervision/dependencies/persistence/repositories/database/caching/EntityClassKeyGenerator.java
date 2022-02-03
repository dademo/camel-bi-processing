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

package fr.dademo.supervision.dependencies.persistence.repositories.database.caching;

import fr.dademo.supervision.dependencies.entities.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author dademo
 */
@Slf4j
public class EntityClassKeyGenerator implements KeyGenerator {

    private static final String DEFAULT_NAME_DELIMITER = "_";

    @Nonnull
    @Override
    public Object generate(@Nonnull Object target,
                           @Nonnull Method method,
                           @Nonnull Object... params) {

        if (params.length > 0) {
            return Optional.ofNullable(params[0])
                .map(this::tryGetId)
                .orElseGet(() -> defaultImplementation(target, method, params));
        } else {
            return defaultImplementation(target, method, params);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    private Object tryGetId(@Nonnull Object firstParam) {

        if (Example.class.isAssignableFrom(firstParam.getClass())) {
            return (((Example<? extends BaseEntity>) firstParam).getProbe()).getId();
        } else if (BaseEntity.class.isAssignableFrom(firstParam.getClass())) {
            return ((BaseEntity) firstParam).getId();
        } else {
            return null;
        }
    }

    private String defaultImplementation(@Nonnull Object target,
                                         @Nonnull Method method,
                                         @Nonnull Object... params) {

        return target.getClass().getSimpleName() + DEFAULT_NAME_DELIMITER
            + method.getName() + DEFAULT_NAME_DELIMITER
            + StringUtils.arrayToDelimitedString(params, DEFAULT_NAME_DELIMITER);
    }
}
