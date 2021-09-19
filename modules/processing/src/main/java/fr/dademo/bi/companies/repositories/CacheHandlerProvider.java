package fr.dademo.bi.companies.repositories;

import javax.annotation.Nullable;

@FunctionalInterface
public interface CacheHandlerProvider {

    @Nullable
    CacheHandler getCacheHandler();
}
