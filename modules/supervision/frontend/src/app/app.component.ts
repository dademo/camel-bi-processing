import { ChangeDetectionStrategy, Component } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AppConfigService, ApplicationState } from './services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  public readonly isStarting: Observable<boolean>;
  public readonly theme: Observable<string>;
  public readonly isDark: Observable<boolean>;

  public get isApplicationError(): boolean {
    return this.appConfig.applicationState === ApplicationState.APPLICATION_ERROR;
  }
  
  constructor(private appConfig: AppConfigService) {
    
    this.isStarting = this._isStarting;
    this.theme = this._theme;
    this.isDark = this._isDark;
  }

  private get _isStarting(): Observable<boolean> {

    return this.appConfig.events
      .pipe(
        map(v => v === ApplicationState.APPLICATION_STARTING)
      );
  }

  private get _theme(): Observable<string> {

    return this.appConfig
      .themeChange
      .pipe(map(theme => `theme-${theme.theme}`));
  }

  private get _isDark(): Observable<boolean> {

    return this.appConfig
      .themeChange
      .pipe(map(theme => theme.isDark));
  }
}
