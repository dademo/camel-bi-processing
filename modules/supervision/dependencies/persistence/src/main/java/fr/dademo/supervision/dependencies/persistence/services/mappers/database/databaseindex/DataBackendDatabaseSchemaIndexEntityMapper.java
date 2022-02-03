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

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.dependencies.persistence.services.mappers.database.databaseindex;

import fr.dademo.supervision.dependencies.backends.model.database.resources.DatabaseIndex;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseindex.DataBackendDatabaseSchemaIndexStatisticsEntity;
import fr.dademo.supervision.dependencies.entities.database.databaseschema.DataBackendDatabaseSchemaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author dademo
 */
@Mapper
public interface DataBackendDatabaseSchemaIndexEntityMapper {

    DataBackendDatabaseSchemaIndexEntityMapper INSTANCE = Mappers.getMapper(DataBackendDatabaseSchemaIndexEntityMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "source.name", target = "name")   // Resolving a conflict
    @Mapping(source = "schema", target = "schema")
    @Mapping(source = "statistics", target = "statistics")
    DataBackendDatabaseSchemaIndexEntity toDataBackendDatabaseIndexEntity(
        DatabaseIndex source,
        DataBackendDatabaseSchemaEntity schema,
        List<DataBackendDatabaseSchemaIndexStatisticsEntity> statistics
    );
}
