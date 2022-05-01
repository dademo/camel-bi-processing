import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { FavouriteLinkDashboard, FavouriteLinkChart } from 'src/app/services';

@Component({
  selector: 'app-sidenav',
  templateUrl: './app-sidenav.component.html',
  styleUrls: ['./app-sidenav.component.scss']
})
export class AppSidenavComponent implements OnInit {

  public get favouriteDashboards(): Observable<readonly FavouriteLinkDashboard[]> {
    return this._favouriteDashboards.asObservable();
  }

  public get favouriteCharts(): Observable<readonly FavouriteLinkChart[]> {
    return this._favouriteCharts.asObservable();
  }

  private readonly _favouriteDashboards: Subject<readonly FavouriteLinkDashboard[]>;
  private readonly _favouriteCharts: Subject<readonly FavouriteLinkChart[]>;

  constructor() {
    
    this._favouriteDashboards = new BehaviorSubject<readonly FavouriteLinkDashboard[]>([
      { url: '/dashboard/1', name: 'Favourite dashboard 1', type: 'dashboard'},
    ]);
    this._favouriteCharts = new BehaviorSubject<readonly FavouriteLinkChart[]>([
      { url: '/toto', name: 'Favourite chart 1', type: 'chart'},
    ]);
  }

  ngOnInit(): void {
    
  }

}
