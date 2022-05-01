export enum ApplicationState {
    APPLICATION_STARTING = 'APPLICATION_STARTING',
    APPLICATION_READY = 'APPLICATION_READY',
    APPLICATION_ERROR = 'APPLICATION_ERROR',
}

export interface ApplicationRuntimeConfiguration {
    readonly serviceRootUrl: string;
    readonly proxyUrl?: string;
}

export interface ApplicationTheme {
    theme: string;
    isDark: boolean;
}

export interface FavouriteLink {
    readonly url: string;
    name?: string;
}

export interface FavouriteLinkDashboard extends FavouriteLink {
    readonly type: 'dashboard';
}

export interface FavouriteLinkChart extends FavouriteLink {
    readonly type: 'chart';
}

export interface ApplicationConfiguration {
    favouriteLinks: readonly FavouriteLink[];
    theme: ApplicationTheme;
}
