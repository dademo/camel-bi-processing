import { Resource } from '@lagoshny/ngx-hateoas-client';
import { BackendState, DataBackendDto, DataBackendResource } from './data-backend';
import { DataBackendDatabaseDto, DataBackendDatabaseResource } from './data-backend-database';

function getDeclaredResources(): Array<new (...args: any[]) => Resource> {
    return [
        DataBackendResource,
        DataBackendDatabaseResource,
    ];
}

export {
    BackendState,
    DataBackendDto,
    DataBackendResource,
    DataBackendDatabaseDto,
    DataBackendDatabaseResource,
    getDeclaredResources,
}
