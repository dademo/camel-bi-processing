import { OverlayContainer } from '@angular/cdk/overlay';
import { Platform } from '@angular/cdk/platform';
import { DOCUMENT } from '@angular/common';
import { Inject, Injectable } from '@angular/core';
import { AppConfigService } from 'src/app/services/app-config.service';
import { ApplicationTheme } from '../services';

@Injectable({
  providedIn: 'root'
})
export class AppOverlayContainer extends OverlayContainer {

  private static readonly DARK_THEME = 'theme-dark';

  private overlayTheme: ApplicationTheme | undefined;

  private get themeClass(): string {

    const _theme = this.overlayTheme?.theme;
    if(_theme) {
      return `theme-${_theme}`;
    } else {
      return '';
    }
  }

  constructor(
    private appConfig: AppConfigService,
    @Inject(DOCUMENT) document: any,
    _platform: Platform) {

    super(document, _platform);
    this.setThemeOnThemeChange();
  }

  public override getContainerElement(): HTMLElement {

    const containerElement = super.getContainerElement();
    this.applyTheme();
    return containerElement;
  }

  private setThemeOnThemeChange(): void {

    this.appConfig.themeChange.subscribe(
      newTheme => this.updateTheme(newTheme)
    );
  }

  private updateTheme(theme: ApplicationTheme): void {

    if(this._containerElement && this.themeClass) {
      this._containerElement.classList.replace(this.themeClass, theme.theme);
      if(theme.isDark) {
        this._containerElement.classList.add(AppOverlayContainer.DARK_THEME);
      } else {
        this._containerElement.classList.remove(AppOverlayContainer.DARK_THEME);
      }
    }
    this.overlayTheme = theme;
  }

  private applyTheme(): void {

    if(this._containerElement && this.themeClass) {
      this._containerElement.classList.add(this.themeClass);
      if(this.overlayTheme?.isDark) {
        this._containerElement.classList.add(AppOverlayContainer.DARK_THEME);
      }
    }
  }
}
