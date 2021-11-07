package fr.dademo.bi.companies.repositories.file.validators;

import fr.dademo.bi.companies.repositories.file.identifier.DataGouvFrFileIdentifier;
import fr.dademo.bi.companies.repositories.file.validators.exception.UnwantedBeanException;
import fr.dademo.bi.companies.services.DataGouvFrDataSetTools;
import fr.dademo.bi.companies.services.datamodel.GouvFrDataSetDefinition;
import fr.dademo.bi.companies.services.datamodel.GouvFrDataSetResourceDefinition;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DataGouvFrValidator implements CachedFileValidator<DataGouvFrFileIdentifier> {

    private DataGouvFrDataSetTools dataGouvFrDataSetTools;

    @Override
    public boolean isValid(@Nonnull DataGouvFrFileIdentifier cachedFileDescription) {

        return latestGouvFrDataSetResourceDefinitionByUrl(cachedFileDescription)
                .map(GouvFrDataSetResourceDefinition::getLastModifiedLocalDateTime)
                .map(lastModified -> lastModified.isEqual(cachedFileDescription.getCachedLastModified()))
                .orElse(true);
    }

    @Override
    @Nonnull
    public List<Class<?>> getBeansByClass() {
        return Collections.singletonList(DataGouvFrDataSetTools.class);
    }

    @Override
    public void onBeanAcquired(@Nonnull Class<?> beanClass, @Nonnull Object bean) {

        if (beanClass.getCanonicalName().equals(DataGouvFrDataSetTools.class.getCanonicalName())) {
            dataGouvFrDataSetTools = (DataGouvFrDataSetTools) bean;
        } else {
            throw new UnwantedBeanException(beanClass);
        }
    }

    private Optional<GouvFrDataSetResourceDefinition> latestGouvFrDataSetResourceDefinitionByUrl(@Nonnull DataGouvFrFileIdentifier cachedFileDescription) {

        return dataGouvFrDataSetTools.getDataSetDefinition(cachedFileDescription.getDataSetName())
                .stream()
                .map(GouvFrDataSetDefinition::getResources)
                .flatMap(Collection::stream)
                .filter(urlFilterFor(cachedFileDescription))
                .max(this::gouvFrDataSetResourceDefinitionComparator);
    }

    private Predicate<GouvFrDataSetResourceDefinition> urlFilterFor(@Nonnull DataGouvFrFileIdentifier cachedFileDescription) {
        return test -> cachedFileDescription.getBaseUrl().toString().equals(test.getUrl());
    }

    private int gouvFrDataSetResourceDefinitionComparator(@Nonnull GouvFrDataSetResourceDefinition o1,
                                                          @Nonnull GouvFrDataSetResourceDefinition o2) {
        return o1.getLastModified().compareTo(o2.getLastModified());
    }
}
