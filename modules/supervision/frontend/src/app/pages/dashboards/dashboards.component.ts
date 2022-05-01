import { Component, OnInit } from '@angular/core';
import { ViewRoute } from 'src/app/components/generic-page-view';
import { AppConfigService } from 'src/app/services';

@Component({
  selector: 'app-dashboards',
  templateUrl: './dashboards.component.html',
  styleUrls: ['./dashboards.component.scss']
})
export class DashboardsComponent implements OnInit {

  public readonly viewRoutes: readonly ViewRoute[] = [
    { applicationRoute: '/dashboards', displayName: 'Dashboards' },
  ];

  constructor(private readonly appConfig: AppConfigService) {

    this.appConfig.setPageTitle('Dashboards');
  }

  ngOnInit(): void {
  }

}
