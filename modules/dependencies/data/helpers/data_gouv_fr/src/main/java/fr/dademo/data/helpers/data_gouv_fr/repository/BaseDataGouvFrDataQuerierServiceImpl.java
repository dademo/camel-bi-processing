/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.repository;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.http.validators.DataGouvFrInputStreamValidator;
import fr.dademo.data.helpers.data_gouv_fr.repository.exception.ResourceNotFoundException;
import jakarta.annotation.Nonnull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * @author dademo
 */
public abstract class BaseDataGouvFrDataQuerierServiceImpl implements DataGouvFrDataQuerierService {

    @Override
    @Nonnull
    public InputStream queryForStream(@Nonnull String dataSetTitle, @Nonnull String resourceTitle) throws IOException, InterruptedException {
        return queryForStream(getDataSet(dataSetTitle), resourceTitle);
    }

    @Nonnull
    @Override
    public InputStream queryForStream(@Nonnull DataGouvFrDataSet dataSet, @Nonnull String resourceTitle) throws IOException, InterruptedException {

        return queryForStream(dataSet
            .getResources().stream()
            .filter(resource -> resourceTitle.equals(resource.getTitle()))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException(resourceTitle, dataSet))
        );
    }

    @Nonnull
    @Override
    public InputStream queryForStream(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource) throws IOException, InterruptedException {

        return queryForStream(
            dataGouvFrDataSetResource,
            DataGouvFrInputStreamValidator.of(dataGouvFrDataSetResource)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList())
        );
    }
}
