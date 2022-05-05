import { AfterViewInit, Component, ComponentRef, Input, OnInit, ViewChild } from '@angular/core';
import { PagedValuesProvider, ViewRoute } from './data-model';
import { GenericPageViewDisplayCardsComponent } from './generic-page-view-display-cards/generic-page-view-display-cards.component';
import { GenericPageViewDisplayListComponent } from './generic-page-view-display-list/generic-page-view-display-list.component';

type ViewType = 'list' | 'card';

@Component({
  selector: 'app-generic-page-view',
  templateUrl: './generic-page-view.component.html',
  styleUrls: ['./generic-page-view.component.scss']
})
export class GenericPageViewComponent implements OnInit, AfterViewInit {

  private static readonly DEFAULT_PAGE: number = 0;
  private static readonly DEFAULT_PAGE_SIZE: number = 25;

  public displayKind: ViewType = 'list';

  @Input()
  public viewRoutes: readonly ViewRoute[] | undefined;
  
  @Input('pagedValuesProvider')
  public _pagedValuesProvider: PagedValuesProvider | undefined;

  @ViewChild(GenericPageViewDisplayListComponent)
  public displayListViewRef: ComponentRef<any> | undefined;

  @ViewChild(GenericPageViewDisplayCardsComponent)
  public displayCardsViewRef: ComponentRef<any> | undefined;

  public get pagedValuesProvider(): PagedValuesProvider {

    if(this._pagedValuesProvider === undefined) {
      throw new Error('GenericPageViewComponent: [this.pagedValuesProvider] must be defined');
    } else {
      return this._pagedValuesProvider;
    }
  }

  constructor() { }

  ngOnInit(): void { }

  ngAfterViewInit(): void { }
}
