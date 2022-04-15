import { HateoasResource, Resource } from "@lagoshny/ngx-hateoas-client";

export declare type BackendState = "READY" | "UNAUTHORIZED" | "UNAVAILABLE" | "NOT_FOUND" | "ERROR";

export interface DataBackendDto {
    id: number;
    backendName: string;
    primaryUrl: string;
    backendState: BackendState;
    backendProductName: string;
    backendProductVersion: string;
    startTime: string;
    backendStateExplanation: string;
    clusterSize: number | null;
    primaryCount: number | null;
    replicaCount: number | null;
    sizeBytes: number | null;
    effectiveSizeBytes: number | null;
    availableSizeBytes: number | null;
    backendStateExecutionsCount: number;
    databasesCount: number | null | undefined;
}

@HateoasResource('data-backend')
export class DataBackendResource extends Resource implements DataBackendDto {

    public id: number = 0;
    public backendName: string = '';
    public primaryUrl: string = '';
    public backendState: BackendState = 'UNAVAILABLE';
    public backendProductName: string = '';
    public backendProductVersion: string = '';
    public startTime: string = '';
    public backendStateExplanation: string = '';
    public clusterSize: number | null = null;
    public primaryCount: number | null = null;
    public replicaCount: number | null = null;
    public sizeBytes: number | null = null;
    public effectiveSizeBytes: number | null = null;
    public availableSizeBytes: number | null = null;
    public backendStateExecutionsCount: number = 0;
    public databasesCount: number | null | undefined;
}
