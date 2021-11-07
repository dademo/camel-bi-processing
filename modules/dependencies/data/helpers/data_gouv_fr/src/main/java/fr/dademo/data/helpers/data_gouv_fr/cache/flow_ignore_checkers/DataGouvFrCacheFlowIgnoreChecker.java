package fr.dademo.data.helpers.data_gouv_fr.cache.flow_ignore_checkers;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.reader.http.data_model.HttpInputStreamIdentifier;
import fr.dademo.tools.cache.validators.CacheFlowIgnoreChecker;

/**
 * As we want to ignore metadata repository, we create this class that will ignore those paths.
 */
@SuppressWarnings("java:S1075")
public class DataGouvFrCacheFlowIgnoreChecker implements CacheFlowIgnoreChecker<InputStreamIdentifier<?>> {

    private static final String IGNORE_PATH = "/api/1/datasets/";

    @Override
    public boolean isFlowAllowedToBeCached(InputStreamIdentifier<?> inputStreamIdentifier) {

        if (inputStreamIdentifier instanceof HttpInputStreamIdentifier) {
            return validate((HttpInputStreamIdentifier) inputStreamIdentifier);
        } else {
            // We don't handle this flow, so we don't disallow it
            return true;
        }
    }

    private boolean validate(HttpInputStreamIdentifier httpInputStreamIdentifier) {
        return !httpInputStreamIdentifier.getUrl().getPath().startsWith(IGNORE_PATH);
    }
}
