package fr.dademo.bi.companies.repositories.file.validators;

import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public interface CachedFileValidator<T extends FileIdentifier<T>> {

    boolean isValid(@Nonnull T cachedFileDescription);

    default void onBeanAcquired(@Nonnull String beanName, @Nonnull Object bean) {
        // Default to no action
    }

    default void onBeanAcquired(@Nonnull Class<?> beanClass, @Nonnull Object bean) {
        // Default to no action
    }

    @Nonnull
    default List<String> getBeansByName() {
        return Collections.emptyList();
    }

    @Nonnull
    default List<Class<?>> getBeansByClass() {
        return Collections.emptyList();
    }
}
