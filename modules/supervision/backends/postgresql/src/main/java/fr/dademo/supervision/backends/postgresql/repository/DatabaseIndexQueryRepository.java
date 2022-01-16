/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.backends.postgresql.repository;

import fr.dademo.supervision.backends.postgresql.repository.entities.DatabaseIndexEntity;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author dademo
 */
@Repository
public interface DatabaseIndexQueryRepository {

    @Nonnull
    List<DatabaseIndexEntity> getDatabaseIndexes();
}
