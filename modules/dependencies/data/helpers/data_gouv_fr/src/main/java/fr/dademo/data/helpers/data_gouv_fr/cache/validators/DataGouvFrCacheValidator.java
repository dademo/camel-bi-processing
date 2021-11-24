/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.cache.validators;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.validators.CacheValidator;
import lombok.AllArgsConstructor;

/**
 * @author dademo
 */
@AllArgsConstructor(staticName = "of")
public class DataGouvFrCacheValidator implements CacheValidator<HttpInputStreamIdentifier> {

    private final DataGouvFrDataSet dataGouvFrDataSet;

    @Override
    public boolean isValid(CachedInputStreamIdentifier<HttpInputStreamIdentifier> cachedInputStreamIdentifier) {
        return cachedInputStreamIdentifier.getTimestamp().isAfter(dataGouvFrDataSet.getLastUpdate());
    }
}
