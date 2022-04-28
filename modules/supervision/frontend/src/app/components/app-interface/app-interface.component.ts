import { Component, OnInit } from '@angular/core';
import { ProgressBarMode } from '@angular/material/progress-bar';
import { map, Observable } from 'rxjs';
import { AppConfigService } from 'src/app/services';

@Component({
  selector: 'app-interface',
  templateUrl: './app-interface.component.html',
  styleUrls: ['./app-interface.component.scss']
})
export class AppInterfaceComponent implements OnInit {

  public readonly pageTitle: Observable<string>;
  public readonly loadingStatus: Observable<ProgressBarMode>;
  public readonly isLoading: Observable<boolean>;

  constructor(private appConfig: AppConfigService) {
    this.pageTitle = this._pageTitle;
    this.loadingStatus = this._loadingStatus;
    this.isLoading = this._isLoading;
  }

  ngOnInit(): void {
  }

  private get _pageTitle(): Observable<string> {
    return this.appConfig.pageTitle;
  }

  private get _loadingStatus(): Observable<ProgressBarMode> {
    return this.appConfig.loadingStatus;
  }

  private get _isLoading(): Observable<boolean> {
    return this.appConfig.isLoading;
  }
}
