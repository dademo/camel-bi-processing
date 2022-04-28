import { Component, OnInit } from '@angular/core';
import { ViewRoute } from 'src/app/components/generic-page-view';
import { AppConfigService } from 'src/app/services';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  // TODO
  public values: Array<object> | undefined = [];

  public readonly viewRoutes: Array<ViewRoute> = [];

  public valuesChanged: boolean = false;

  constructor(private readonly appConfig: AppConfigService) {
    
    this.appConfig.setPageTitle('Home');
  }

  ngOnInit(): void {
  }

}
