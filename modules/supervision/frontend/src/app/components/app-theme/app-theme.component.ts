import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { BehaviorSubject, map, Observable, Subject } from 'rxjs';
import { AppConfigService, ApplicationTheme } from 'src/app/services';

interface ThemeDefinition {
  theme: string,
  displayName: string,
  enabled: boolean,
}

@Component({
  selector: 'app-theme',
  templateUrl: './app-theme.component.html',
  styleUrls: ['./app-theme.component.scss'],
  changeDetection: ChangeDetectionStrategy.Default,
})
export class AppThemeComponent implements OnInit {

  public readonly currentThemeClass: Observable<string>;
  public readonly isDarkTheme: Observable<boolean>;
  public readonly availableThemes: Observable<readonly ThemeDefinition[]>;

  private readonly availableThemesSubject: Subject<readonly ThemeDefinition[]>;
  private _applicationTheme: ApplicationTheme;

  constructor(
    private readonly appConfig: AppConfigService,
    private readonly cdRef: ChangeDetectorRef,
  ) {

    this._applicationTheme = AppConfigService.DEFAULT_THEME;
    this.currentThemeClass = this._theme;
    this.isDarkTheme = this._isDarkTheme;
    this.availableThemesSubject = new BehaviorSubject<readonly ThemeDefinition[]>(this._themes);
    this.availableThemes = this.availableThemesSubject.asObservable();

    this.appConfig
      .themeChange
      .subscribe(newTheme => this.onThemeChange(newTheme));
  }

  ngOnInit(): void {
  }

  public setApplicationTheme(name: string): void {
    this.appConfig.setTheme(name);
  }

  public setIsDark(value: boolean): void {
    this.appConfig.setIsDark(value);
  }

  private onThemeChange(newTheme: ApplicationTheme): void {

    this._applicationTheme = newTheme;
    this.availableThemesSubject.next(this._themes);
    this.cdRef.markForCheck();
  }

  private get _theme(): Observable<string> {

    return this.appConfig
      .themeChange
      .pipe(map(theme => `theme-${theme.theme}`));
  }

  private get _isDarkTheme(): Observable<boolean> {

    return this.appConfig
      .themeChange
      .pipe(map(theme => theme.isDark));
  }

  private get _themes(): readonly ThemeDefinition[] {

    return [
      { theme: 'indigo-pink', displayName: 'Indigo & Pink' },
      { theme: 'deep-purple-amber', displayName: 'Deep Purple & Amber' },
      { theme: 'pink-blue-grey', displayName: 'Pink & Blue Grey' },
      { theme: 'purple-green', displayName: 'Purple & Green' },
    ]
    .map(
      v => <ThemeDefinition>{
        theme: v.theme,
        displayName: v.displayName,
        enabled: this._applicationTheme.theme === v.theme,
      }
    );
  }

}
