import { HateoasResource, Resource } from "@lagoshny/ngx-hateoas-client";

export interface DataBackendDatabaseDto {
    id: number;
    dataBackendDescriptionId: number;
    name: string;
    databaseStatisticsCount: number;
    schemasCount: number;
}

@HateoasResource('database')
export class DataBackendDatabaseResource extends Resource implements DataBackendDatabaseDto {

    public id: number = 0;
    public dataBackendDescriptionId: number = 0;
    public name: string = '';
    public databaseStatisticsCount: number = 0;
    public schemasCount: number = 0;
}
