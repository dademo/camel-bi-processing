package fr.dademo.tools.cache.validators;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeOutCacheValidator<T extends InputStreamIdentifier<?>> implements CacheValidator<T> {

    @Nonnull
    private final Duration cacheDuration;

    public static <T extends InputStreamIdentifier<?>> TimeOutCacheValidator<T> of(Duration cacheDuration) {
        return new TimeOutCacheValidator<>(cacheDuration);
    }

    public static <T extends InputStreamIdentifier<?>> TimeOutCacheValidator<T> ofDays(long days) {
        return new TimeOutCacheValidator<>(Duration.ofDays(days));
    }

    @Override
    public boolean isValid(CachedInputStreamIdentifier<T> cachedInputStreamIdentifier) {

        return cachedInputStreamIdentifier.getTimestamp()
                .plus(cacheDuration)
                .isBefore(LocalDateTime.now());
    }
}
