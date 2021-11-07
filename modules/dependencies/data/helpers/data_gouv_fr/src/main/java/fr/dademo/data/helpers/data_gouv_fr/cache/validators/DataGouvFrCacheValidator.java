package fr.dademo.data.helpers.data_gouv_fr.cache.validators;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import fr.dademo.tools.cache.validators.CacheValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class DataGouvFrCacheValidator implements CacheValidator<HttpInputStreamIdentifier> {

    private final DataGouvFrDataSet dataGouvFrDataSet;

    @Override
    public boolean isValid(CachedInputStreamIdentifier<HttpInputStreamIdentifier> cachedInputStreamIdentifier) {
        return cachedInputStreamIdentifier.getTimestamp().isAfter(dataGouvFrDataSet.getLastUpdate());
    }
}
