import { AfterViewInit, Component, ComponentRef, Input, OnInit, ViewChild } from '@angular/core';
import { PagedResourceCollection, Resource } from '@lagoshny/ngx-hateoas-client';
import { SortedPageParam } from '@lagoshny/ngx-hateoas-client/lib/model/declarations';
import { Observable } from 'rxjs';
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation, PagedValuesProvider, ViewRoute } from './data-model';
import { GenericPageViewDataSource } from './generic-page-view-data-source';
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
  
  @Input()
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  public pagedValuesProvider: PagedValuesProvider;

  @ViewChild(GenericPageViewDisplayListComponent)
  public displayListViewRef: ComponentRef<any> | undefined;

  @ViewChild(GenericPageViewDisplayCardsComponent)
  public displayCardsViewRef: ComponentRef<any> | undefined;

  constructor() { }

  ngOnInit(): void {

    if(!Boolean(this.pagedValuesProvider)) {
      throw new Error('GenericPageViewComponent: [this.pagedValuesProvider] must be defined');
    }
  }

  ngAfterViewInit(): void { }
}
