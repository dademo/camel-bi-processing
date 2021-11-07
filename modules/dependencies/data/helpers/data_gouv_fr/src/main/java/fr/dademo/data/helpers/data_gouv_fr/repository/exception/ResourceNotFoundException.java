package fr.dademo.data.helpers.data_gouv_fr.repository.exception;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;

import javax.annotation.Nonnull;

public class ResourceNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -2396308059854053152L;

    public ResourceNotFoundException(@Nonnull String resourceName,
                                     @Nonnull DataGouvFrDataSet dataGouvFrDataSet) {
        super(String.format("Resource `%s` not found in data set `%s`", resourceName, dataGouvFrDataSet.getTitle()));
    }
}
