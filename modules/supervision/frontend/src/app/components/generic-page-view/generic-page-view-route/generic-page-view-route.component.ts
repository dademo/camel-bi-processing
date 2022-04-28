import { Component, Input, OnInit } from '@angular/core';
import { ViewRoute } from 'src/app/components/generic-page-view';

@Component({
  selector: 'app-generic-page-view-route',
  templateUrl: './generic-page-view-route.component.html',
  styleUrls: ['./generic-page-view-route.component.scss']
})
export class GenericPageViewRouteComponent implements OnInit {

  @Input()
  public viewRoutes: Array<ViewRoute> | undefined;

  constructor() {
    this.viewRoutes = [];
  }

  ngOnInit(): void {
  }

}
