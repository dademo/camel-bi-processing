export enum ApplicationState {
    APPLICATION_STARTING = 'APPLICATION_STARTING',
    APPLICATION_READY = 'APPLICATION_READY',
    APPLICATION_ERROR = 'APPLICATION_ERROR',
}

export interface ApplicationConfiguration {
    serviceRootUrl: string,
    proxyUrl?: string,
}

export interface FavouriteLink {
    url: string,
    name?: string,
}

export interface FavouriteLinkDashboard extends FavouriteLink {
    type: 'dashboard',
}

export interface FavouriteLinkChart extends FavouriteLink {
    type: 'chart',
}
