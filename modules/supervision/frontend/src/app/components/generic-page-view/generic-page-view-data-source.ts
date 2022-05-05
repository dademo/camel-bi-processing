import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { PageEvent } from "@angular/material/paginator";
import { Sort } from "@angular/material/sort";
import { SortedPageParam } from "@lagoshny/ngx-hateoas-client/lib/model/declarations";
import { first, firstValueFrom, map, Observable, of, ReplaySubject, tap, zip } from "rxjs";
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation, PagedValuesProvider } from "./data-model";

export class GenericPageViewDataSource extends DataSource<GenericPageViewDataRepresentation> {

    private static readonly DEFAULT_PAGE_INDEX: number = 0;
    private static readonly DEFAULT_PAGE_SIZE: number = 25;

    private readonly valuesSubject: ReplaySubject<readonly GenericPageViewDataRepresentation[]>;

    private _page: PageEvent;
    private _sort: Sort;

    public get page(): PageEvent {
        return this._page;
    }

    public get sort(): Sort {
        return this._sort;
    }

    constructor(
        private readonly pagedValuesProvider: PagedValuesProvider,
        private readonly pageChangeEvent?: Observable<PageEvent>,
        private readonly sortChangeEvent?: Observable<Sort>,) {

            super();

            this._sort = {
                active: '',
                direction: 'asc',
            };
            this._page = {
                length: 0,
                pageIndex: GenericPageViewDataSource.DEFAULT_PAGE_INDEX,
                pageSize: GenericPageViewDataSource.DEFAULT_PAGE_SIZE,
            };

            this.valuesSubject = new ReplaySubject<readonly GenericPageViewDataRepresentation[]>(1);
            if(this.pageChangeEvent && this.sortChangeEvent) {
                this.pageChangeEvent.subscribe(pageEvent => this.onPageChangeEvent(pageEvent));
                this.sortChangeEvent.subscribe(sortEvent => this.onSortChangeEvent(sortEvent));
                this.getCurrentPage().subscribe(v => this.valuesSubject.next(v));
            } else {
                this.fetchAllPages().then(v => this.valuesSubject.next(v));
            }
        }

    connect(collectionViewer: CollectionViewer): Observable<readonly GenericPageViewDataRepresentation[]> {
        return this.valuesSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
    }

    private getCurrentPage(): Observable<readonly GenericPageViewDataRepresentation[]> {
        
        console.log('getCurrentPage');
        const _sort: {[key: string]: 'ASC' | 'DESC'} = {};
        if(this._sort) {
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            _sort[this._sort.active] = this._sort?.direction.toUpperCase();
        }

        return this.fetchPage({
            pageParams: {
                page: this._page?.pageIndex,
                size: this._page?.pageSize,
            },
            sort: _sort,
        }).pipe(map(pageViewDataRepresentation => pageViewDataRepresentation.resources));
    }

    private async fetchAllPages(): Promise<readonly GenericPageViewDataRepresentation[]> {

        const sort = {};
        const firstPage = await firstValueFrom(this.fetchPage({
            pageParams: {
                page: GenericPageViewDataSource.DEFAULT_PAGE_INDEX,
                size: GenericPageViewDataSource.DEFAULT_PAGE_SIZE,
            },
            sort: sort,
        }));

        let _observables: Observable<GenericPageViewDataCollectionRepresentation>[] = [];
        _observables.push(of(firstPage));

        for(let it = 1; it < firstPage.totalPages; it++) {
            _observables.push(
                this.fetchPage({
                    pageParams: {
                        page: it,
                        size: GenericPageViewDataSource.DEFAULT_PAGE_SIZE,
                    },
                    sort: sort,
                }).pipe(
                    first(),
                )
            );
        }

        return (await firstValueFrom(zip(..._observables)))
            .map(pageViewDataRepresentation => pageViewDataRepresentation.resources)
            .reduce((previousValue, currentValue) => previousValue.concat(currentValue), []);
    }

    private onPageChangeEvent(pageEvent: PageEvent): void {
        this._page = pageEvent;
        this.refreshPage();
    }

    private onSortChangeEvent(sortEvent: Sort): void {
        this._sort = sortEvent;
        this.refreshPage();
    }

    private refreshPage(): void {

        const _sort: {[key: string]: 'ASC' | 'DESC'} = {};
        if(this._sort) {
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            _sort[this._sort.active] = (this._sort?.direction.toUpperCase() || 'ASC');
        }

        this.fetchPage({
                pageParams: {
                    page: this._page?.pageIndex,
                    size: this._page?.pageSize,
                },
                sort: _sort,
            })
            .pipe(map(pageViewDataRepresentation => pageViewDataRepresentation.resources))
            .subscribe(this.valuesSubject.next);
    }

    private fetchPage(sortedPageParam: SortedPageParam): Observable<GenericPageViewDataCollectionRepresentation> {

        return this.pagedValuesProvider(sortedPageParam)
            .pipe(tap({
                next: (pageViewDataRepresentation: GenericPageViewDataCollectionRepresentation) => {
                    this._page.length = pageViewDataRepresentation.totalElements;
                }
            }));
    }
}