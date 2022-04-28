import { Component, OnInit } from '@angular/core';
import { ViewRoute } from 'src/app/components/generic-page-view';
import { AppConfigService } from 'src/app/services';

@Component({
  selector: 'app-data-backends',
  templateUrl: './data-backends.component.html',
  styleUrls: ['./data-backends.component.scss']
})
export class DataBackendsComponent implements OnInit {

  // TODO
  public values: Array<object> | undefined = [];

  public readonly viewRoutes: Array<ViewRoute> = [
    { applicationRoute: '/data-backends', displayName: 'Data backends' },
  ];

  public valuesChanged: boolean = false;

  constructor(private readonly appConfig: AppConfigService) {
    
    this.appConfig.setPageTitle('Data backends');
  }

  ngOnInit(): void {
  }

}
