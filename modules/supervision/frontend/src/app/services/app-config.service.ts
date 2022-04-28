import { Location } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ProgressBarMode } from '@angular/material/progress-bar';
import { Title } from '@angular/platform-browser';
import { BehaviorSubject, Observable, Subject, lastValueFrom } from 'rxjs';

import { environment } from 'src/environments/environment';
import { ApplicationConfiguration, ApplicationState, ApplicationTheme } from './data-model';

@Injectable({
  providedIn: 'root'
})
export class AppConfigService {

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

  public get applicationConfiguration(): ApplicationConfiguration | undefined {
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

  private _applicationConfiguration: ApplicationConfiguration | undefined;

  private _error: Error | undefined;


  // GUI
  private readonly _pageTitle: Subject<string>;
  private readonly _loadingStatus: Subject<ProgressBarMode>;
  private readonly _isLoading: Subject<boolean>;
  private readonly _themeChange: Subject<ApplicationTheme>;


  constructor(
    private location: Location,
    private http: HttpClient,
    private title: Title) {

      this._applicationState = ApplicationState.APPLICATION_STARTING;
      this._theme = AppConfigService.DEFAULT_THEME;

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

  public setpageTitle(pageTitle: string): void {
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
  }

  public setTheme(name: string): void {
    this._theme.theme = name;
    this._themeChange.next(this._theme);
  }

  private fetchConfiguration(): void {

    this.http.get<ApplicationConfiguration>(this.appConfigurationUrl)
      .subscribe({
        next: this.onConfigurationFetchSuccess.bind(this),
        error: this.onConfigurationFetchError.bind(this),
      });
  }

  private onConfigurationFetchSuccess(applicationConfiguration: ApplicationConfiguration): void {

    this._applicationConfiguration = applicationConfiguration;
    this._events.next(ApplicationState.APPLICATION_READY);
  }

  private onConfigurationFetchError(error: HttpErrorResponse): void {

    this._error = error;
    this._events.next(ApplicationState.APPLICATION_ERROR);
  }

  private get appConfigurationUrl(): string {
    return this.location.prepareExternalUrl(environment.applicationConfigurationRelativePath);
  }
}
