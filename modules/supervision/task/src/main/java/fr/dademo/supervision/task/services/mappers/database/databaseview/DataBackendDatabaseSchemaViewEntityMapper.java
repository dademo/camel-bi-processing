/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.task.services.mappers.database.databaseview;

import fr.dademo.supervision.backends.model.database.resources.DatabaseView;
import fr.dademo.supervision.entities.database.databaseview.DataBackendDatabaseSchemaViewEntity;
import fr.dademo.supervision.entities.database.databaseview.DataBackendDatabaseSchemaViewStatisticsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaViewEntityMapper {

    DataBackendDatabaseSchemaViewEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaViewEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "schema", ignore = true)
    @Mapping(source = "statistics", target = "statistics")
    DataBackendDatabaseSchemaViewEntity toDataBackendDatabaseViewEntity(
        DatabaseView source,
        List<DataBackendDatabaseSchemaViewStatisticsEntity> statistics
    );
}
