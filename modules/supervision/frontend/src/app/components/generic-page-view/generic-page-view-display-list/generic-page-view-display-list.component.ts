import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { SortedPageParam } from '@lagoshny/ngx-hateoas-client/lib/model/declarations';
import { map, Observable, Subject, tap } from 'rxjs';
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation, PagedValuesProvider } from '../data-model';

@Component({
  selector: 'app-generic-page-view-display-list',
  templateUrl: './generic-page-view-display-list.component.html',
  styleUrls: ['./generic-page-view-display-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayListComponent implements OnInit {

  private static readonly DEFAULT_PAGE_INDEX: number = 0;
  private static readonly DEFAULT_PAGE_SIZE: number = 25;

  public readonly pageSizeOptions: number[] = [10, 25, 100];

  public totalElements: number | undefined;
  public pageSize: number | undefined;
  public pageIndex: number | undefined;
  public readonly dataRepresentations: Observable<readonly GenericPageViewDataRepresentation[]>;

  @Input('pagedValuesProvider')
  public _pagedValuesProvider: PagedValuesProvider | undefined;

  private get pagedValuesProvider(): PagedValuesProvider {

    if(this._pagedValuesProvider === undefined) {
      throw new Error('GenericPageViewDisplayListComponent: [this.pagedValuesProvider] must be defined');
    } else {
      return this._pagedValuesProvider;
    }
  }
  
  private _dataRepresentations: Subject<readonly GenericPageViewDataRepresentation[]>;

  constructor() {

    this._dataRepresentations = new Subject<readonly GenericPageViewDataRepresentation[]>();
    this.dataRepresentations = this._dataRepresentations.asObservable();
  }

  ngOnInit(): void {
    this.refreshPageValues();
  }

  public onPageChange(pageEvent: PageEvent): void {
    
    this.pageIndex = pageEvent.pageIndex;
    this.pageSize = pageEvent.pageSize;
    this.refreshPageValues();
  }

  private refreshPageValues(): void {

    const _sort: {[key: string]: 'ASC' | 'DESC'} = {};

    this.fetchPage({
        pageParams: {
            page: this.pageIndex || GenericPageViewDisplayListComponent.DEFAULT_PAGE_INDEX,
            size: this.pageSize || GenericPageViewDisplayListComponent.DEFAULT_PAGE_SIZE,
        },
        sort: _sort,
      })
      .pipe(map(pageViewDataRepresentation => pageViewDataRepresentation.resources))
      .subscribe(v => this._dataRepresentations.next(v));
  }

  private fetchPage(sortedPageParam: SortedPageParam): Observable<GenericPageViewDataCollectionRepresentation> {

    return this.pagedValuesProvider(sortedPageParam)
        .pipe(tap({
            next: (pageViewDataRepresentation: GenericPageViewDataCollectionRepresentation) => {
                this.totalElements = pageViewDataRepresentation.totalElements;
            }
        }));
  }
}
