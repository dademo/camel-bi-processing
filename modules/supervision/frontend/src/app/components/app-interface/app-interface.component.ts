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

  public get pageTitle(): Observable<string> {
    return this.appConfig.pageTitle;
  }

  public get loadingStatus(): Observable<ProgressBarMode> {
    return this.appConfig.loadingStatus;
  }

  public get isLoading(): Observable<boolean> {

    return this.appConfig.isLoading;
  }

  constructor(private appConfig: AppConfigService) { }

  ngOnInit(): void {
  }
}
