import { Component } from '@angular/core';
import { firstValueFrom, map, Observable } from 'rxjs';
import { AppConfigService, ApplicationState } from './services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  constructor(private appConfig: AppConfigService) {
    
  }

  public get isStarting(): Observable<boolean> {

    return this.appConfig.events
      .pipe(
        map(v => v === ApplicationState.APPLICATION_STARTING)
      );
  }

  public get isApplicationError(): boolean {
    return this.appConfig.applicationState === ApplicationState.APPLICATION_ERROR;
  }
}
