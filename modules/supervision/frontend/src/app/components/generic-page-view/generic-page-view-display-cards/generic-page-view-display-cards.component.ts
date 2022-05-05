import { Component, OnInit, ChangeDetectionStrategy, Input } from '@angular/core';
import { BehaviorSubject, first, map, mergeMap, Observable, of, reduce, Subject, switchMap, switchMapTo, zip } from 'rxjs';
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation, PagedValuesProvider } from '../data-model';

@Component({
  selector: 'app-generic-page-view-display-cards',
  templateUrl: './generic-page-view-display-cards.component.html',
  styleUrls: ['./generic-page-view-display-cards.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayCardsComponent implements OnInit {

  private static readonly DEFAULT_PAGE_INDEX: number = 0;
  private static readonly DEFAULT_PAGE_SIZE: number = 25;

  @Input('pagedValuesProvider')
  public _pagedValuesProvider: PagedValuesProvider | undefined;

  public readonly dataRepresentations: Observable<readonly GenericPageViewDataRepresentation[]>;

  private readonly _dataRepresentations: Subject<readonly GenericPageViewDataRepresentation[]>;

  private get pagedValuesProvider(): PagedValuesProvider {

    if(this._pagedValuesProvider === undefined) {
      throw new Error('GenericPageViewDisplayCardsComponent: [this.pagedValuesProvider] must be defined');
    } else {
      return this._pagedValuesProvider;
    }
  }

  constructor() {

    this._dataRepresentations = new BehaviorSubject<readonly GenericPageViewDataRepresentation[]>([]);
    this.dataRepresentations = this._dataRepresentations.asObservable();
  }

  ngOnInit(): void {
    this.fetchAllPages().subscribe(
      genericPageViewDataRepresentation => this._dataRepresentations.next(genericPageViewDataRepresentation)
    );
  }

  private fetchAllPages(): Observable<readonly GenericPageViewDataRepresentation[]> {

    return this.pagedValuesProvider({
        pageParams: {
            page: GenericPageViewDisplayCardsComponent.DEFAULT_PAGE_INDEX,
            size: GenericPageViewDisplayCardsComponent.DEFAULT_PAGE_SIZE,
        },
        sort: {},
    }).pipe(
      mergeMap(firstPage => this.fetchAllPagesWithFirstPage(firstPage)),
      switchMap(page => page),
      map(page => page.resources),
      reduce<readonly GenericPageViewDataRepresentation[], readonly GenericPageViewDataRepresentation[]>((accumulator, currentValue) => accumulator.concat(currentValue), [])
    );
  }

  private fetchAllPagesWithFirstPage(firstPage: GenericPageViewDataCollectionRepresentation): Observable<GenericPageViewDataCollectionRepresentation>[] {
    
    let _observables: Observable<GenericPageViewDataCollectionRepresentation>[] = [];
    _observables.push(of(firstPage));

    for(let it = 1; it < firstPage.totalPages; it++) {
      _observables.push(
          this.pagedValuesProvider({
              pageParams: {
                  page: it,
                  size: GenericPageViewDisplayCardsComponent.DEFAULT_PAGE_SIZE,
              },
              sort: {},
          }).pipe(
              first(),
          )
      );
    }

    return _observables;
  }
}
