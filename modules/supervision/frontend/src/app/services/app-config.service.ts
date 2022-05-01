import { Location } from '@angular/common';
import { HttpClient, HttpErrorResponse, JsonpClientBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProgressBarMode } from '@angular/material/progress-bar';
import { Title } from '@angular/platform-browser';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { environment } from 'src/environments/environment';
import { ApplicationConfiguration, ApplicationRuntimeConfiguration, ApplicationState, ApplicationTheme, FavouriteLink } from './data-model';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {

  private static readonly KEY_APPLICATION_CONFIGURATION = 'app_config';

  private static readonly DEFAULT_APP_TITLE = "No title";
  public static readonly DEFAULT_THEME: ApplicationTheme = {
    //theme: 'indigo-pink',
    theme: 'deep-purple-amber',
    isDark: false,
  };

  public get events(): Observable<ApplicationState> {
    return this._events.asObservable();
  }
  
  public get pageTitle(): Observable<string> {
    return this._pageTitle.asObservable();
  }
  
  public get loadingStatus(): Observable<ProgressBarMode> {
    return this._loadingStatus.asObservable();
  }
  
  public get isLoading(): Observable<boolean> {
    return this._isLoading.asObservable();
  }

  public get applicationState(): ApplicationState {
    return this._applicationState;
  }

  public get applicationRuntimeConfiguration(): ApplicationRuntimeConfiguration | undefined {
    return Object.assign({}, this._applicationConfiguration);
  }

  public get error(): Error | undefined {
    return this._error;
  }

  public get themeChange(): Observable<ApplicationTheme> {
    return this._themeChange.asObservable();
  }


  private readonly _events: Subject<ApplicationState>;
  private _applicationState: ApplicationState;
  private _theme: ApplicationTheme;
  private _favouriteLinks: readonly FavouriteLink[];
  private _applicationConfiguration: ApplicationRuntimeConfiguration | undefined;
  private _error: Error | undefined;


  // GUI
  private readonly _pageTitle: Subject<string>;
  private readonly _loadingStatus: Subject<ProgressBarMode>;
  private readonly _isLoading: Subject<boolean>;
  private readonly _themeChange: Subject<ApplicationTheme>;

  private readonly storage: Storage;


  constructor(
    private location: Location,
    private http: HttpClient,
    private title: Title,
    localStorageService: LocalStorageService) {

      this.storage = localStorageService.localStorage;

      const appConfig = this.loadApplicationConfiguration();

      this._applicationState = ApplicationState.APPLICATION_STARTING;
      this._theme = appConfig?.theme || AppConfigService.DEFAULT_THEME;
      this._favouriteLinks = appConfig?.favouriteLinks || [];

      this._events = new BehaviorSubject<ApplicationState>(ApplicationState.APPLICATION_STARTING);
      this._pageTitle = new BehaviorSubject(AppConfigService.DEFAULT_APP_TITLE);
      this._loadingStatus = new BehaviorSubject<ProgressBarMode>('indeterminate');
      this._isLoading = new BehaviorSubject<boolean>(false);
      this._themeChange = new BehaviorSubject<ApplicationTheme>(this._theme);

      this.setOnConfigurationFetched();
      this.fetchConfiguration();
    }

  private setOnConfigurationFetched() {
    this._events.subscribe(applicationState => this._applicationState = applicationState);
  }

  public setPageTitle(pageTitle: string): void {
    this._pageTitle.next(pageTitle);
  }

  public setAppTitle(appTitle: string): void {
    this.title.setTitle(appTitle);
  }

  public setLoadingStatus(loadingStatus: ProgressBarMode): void {
    this._loadingStatus.next(loadingStatus);
  }

  public setIsLoading(isLoading: boolean): void {
    this._isLoading.next(isLoading);
  }

  public setIsDark(value: boolean): void {
    this._theme.isDark = value;
    this._themeChange.next(this._theme);
    this.onConfigurationChanged();
  }

  public setTheme(name: string): void {
    this._theme.theme = name;
    this._themeChange.next(this._theme);
    this.onConfigurationChanged();
  }

  // Internal actions

  private get appConfigurationUrl(): string {
    return this.location.prepareExternalUrl(environment.applicationConfigurationRelativePath);
  }

  private fetchConfiguration(): void {

    this.http.get<ApplicationRuntimeConfiguration>(this.appConfigurationUrl)
      .subscribe({
        next: this.onConfigurationFetchSuccess.bind(this),
        error: this.onConfigurationFetchError.bind(this),
      });
  }

  private onConfigurationFetchSuccess(applicationConfiguration: ApplicationRuntimeConfiguration): void {

    this._applicationConfiguration = applicationConfiguration;
    this._events.next(ApplicationState.APPLICATION_READY);
  }

  private onConfigurationFetchError(error: HttpErrorResponse): void {

    this._error = error;
    this._events.next(ApplicationState.APPLICATION_ERROR);
  }

  // Events
  private onConfigurationChanged(): void {
    this.persistApplicationConfiguration();
  }

  private persistApplicationConfiguration(): void {
    this.storage.setItem(AppConfigService.KEY_APPLICATION_CONFIGURATION, JSON.stringify(this.applicationConfiguration));
  }

  private loadApplicationConfiguration(): ApplicationConfiguration | undefined {

    try {
      return JSON.parse(this.storage.getItem(AppConfigService.KEY_APPLICATION_CONFIGURATION) || '') || undefined;
    } catch {
      return undefined;
    }
  }

  // Configuration
  private get applicationConfiguration(): ApplicationConfiguration {

    return {
      favouriteLinks: this._favouriteLinks,
      theme: this._theme,
    }
  }
}
