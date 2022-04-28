import { Component, OnInit } from '@angular/core';
import { ViewRoute } from 'src/app/components/generic-page-view';
import { AppConfigService } from 'src/app/services';

@Component({
  selector: 'app-dashboard-view',
  templateUrl: './dashboard-view.component.html',
  styleUrls: ['./dashboard-view.component.scss']
})
export class DashboardViewComponent implements OnInit {

  public readonly viewRoutes: Array<ViewRoute> = [
    { applicationRoute: '/dashboards', displayName: 'Dashboards' },
    { applicationRoute: '/dashboard/1', displayName: 'This dashboard (#TODO)' },
  ];

  constructor(private readonly appConfig: AppConfigService) {
    
    this.appConfig.setPageTitle('Dashboard 1 (#TODO)');
  }

  ngOnInit(): void {
  }

}
