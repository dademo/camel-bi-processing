package fr.dademo.tools.cache.repository;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

public interface CacheLockRepository<T extends InputStreamIdentifier<?>> {

    List<CachedInputStreamIdentifier<T>> readLockFile();

    void persistLockFile(List<CachedInputStreamIdentifier<T>> lockFileContent) throws IOException;

    void withLockedLockFile(Runnable onLockAcquired);

    <R> R withLockedLockFile(Supplier<R> onLockAcquired);
}
