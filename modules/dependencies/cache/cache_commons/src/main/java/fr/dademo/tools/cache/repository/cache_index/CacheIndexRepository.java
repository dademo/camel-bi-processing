/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.repository.cache_index;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.lock.repository.model.Lock;

import java.io.IOException;
import java.util.List;

/**
 * @author dademo
 */
public interface CacheIndexRepository<T extends InputStreamIdentifier<?>> {

    Lock acquireLock();

    List<CachedInputStreamIdentifier<T>> readIndex();

    void persistIndex(List<CachedInputStreamIdentifier<T>> indexContent) throws IOException;
}
