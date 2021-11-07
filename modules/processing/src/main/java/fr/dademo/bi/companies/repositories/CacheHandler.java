package fr.dademo.bi.companies.repositories;

import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;
import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;

import javax.annotation.Nonnull;
import java.io.InputStream;
import java.util.List;

public interface CacheHandler {

    boolean hasCachedInputStream(@Nonnull FileIdentifier<?> fileIdentifier);

    InputStream readFromCachedInputStream(@Nonnull FileIdentifier<?> fileIdentifier);

    InputStream cacheInputStream(@Nonnull InputStream inputStream,
                                 @Nonnull FileIdentifier<?> fileIdentifier,
                                 @Nonnull List<HashDefinition> hashProvider);
}
