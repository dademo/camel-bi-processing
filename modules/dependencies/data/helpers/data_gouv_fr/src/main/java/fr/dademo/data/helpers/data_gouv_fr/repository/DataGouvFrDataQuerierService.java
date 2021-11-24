/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.helpers.data_gouv_fr.repository;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSetPage;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.http.validators.DataGouvFrInputStreamValidator;
import fr.dademo.data.helpers.data_gouv_fr.repository.query.DataGouvFrMetadataServiceMetadataQueryOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author dademo
 */
public interface DataGouvFrDataQuerierService {

    @Nonnull
    DataGouvFrDataSetPage listDataSets(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions) throws IOException;

    @Nonnull
    DataGouvFrDataSet getDataSet(@Nonnull String dataSetTitle) throws IOException;

    @Nonnull
    InputStream queryForStream(@Nonnull String dataSetTitle, @Nonnull String resourceTitle) throws IOException;

    @Nonnull
    InputStream queryForStream(@Nonnull DataGouvFrDataSet dataSet, @Nonnull String resourceTitle) throws IOException;

    @Nonnull
    InputStream queryForStream(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource) throws IOException;

    @Nonnull
    InputStream queryForStream(@Nonnull DataGouvFrDataSetResource dataGouvFrDataSetResource,
                               @Nonnull List<? extends DataGouvFrInputStreamValidator> inputStreamIdentifierValidators) throws IOException;
}
