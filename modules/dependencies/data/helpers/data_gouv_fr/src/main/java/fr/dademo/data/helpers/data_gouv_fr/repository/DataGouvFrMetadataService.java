package fr.dademo.data.helpers.data_gouv_fr.repository;

import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSet;
import fr.dademo.data.definitions.data_gouv_fr.DataGouvFrDataSetPage;
import fr.dademo.data.definitions.data_gouv_fr.dimensions.DataGouvFrDataSetResource;
import fr.dademo.data.helpers.data_gouv_fr.repository.query.DataGouvFrMetadataServiceMetadataQueryOptions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;

public interface DataGouvFrMetadataService {

    DataGouvFrDataSetPage listDataSets(@Nullable DataGouvFrMetadataServiceMetadataQueryOptions queryOptions);

    DataGouvFrDataSet getDataSet(@Nonnull String dataSetName);

    InputStream queryForStream(DataGouvFrDataSetResource dataGouvFrDataSetResource);
}
