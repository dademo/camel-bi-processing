package fr.dademo.bi.companies.services;

import fr.dademo.bi.companies.repositories.datamodel.HashDefinition;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface DataGouvFrHashGetter {

    Optional<HashDefinition> hashDefinitionOfDataSetResourceByTitle(@Nonnull String dataSetName,
                                                                    @Nonnull String resourceTitle);

    default Optional<HashDefinition> hashDefinitionOfDataSetResourceByUrl(@Nonnull String dataSetName,
                                                                          @Nonnull String resourceUrl) {
        return hashDefinitionOfDataSetResourceByUrl(dataSetName, resourceUrl, false);
    }

    Optional<HashDefinition> hashDefinitionOfDataSetResourceByUrl(@Nonnull String dataSetName,
                                                                  @Nonnull String resourceUrl,
                                                                  boolean compareUrlQuery);
}
