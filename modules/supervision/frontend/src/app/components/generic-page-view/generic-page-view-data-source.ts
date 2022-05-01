import { CollectionViewer, DataSource } from "@angular/cdk/collections";
import { PageEvent } from "@angular/material/paginator";
import { Sort } from "@angular/material/sort";
import { SortedPageParam } from "@lagoshny/ngx-hateoas-client/lib/model/declarations";
import { first, firstValueFrom, map, Observable, of, ReplaySubject, zip } from "rxjs";
import { GenericPageViewDataCollectionRepresentation, GenericPageViewDataRepresentation, PagedValuesProvider } from "./data-model";

export class GenericPageViewDataSource extends DataSource<GenericPageViewDataRepresentation> {

    private static readonly DEFAULT_PAGE_INDEX: number = 0;
    private static readonly DEFAULT_PAGE_SIZE: number = 25;

    private readonly valuesSubject: ReplaySubject<readonly GenericPageViewDataRepresentation[]>;

    private page: PageEvent | undefined;
    private sort: Sort | undefined;

    constructor(
        private readonly pagedValuesProvider: PagedValuesProvider,
        private readonly pageChangeEvent?: Observable<PageEvent>,
        private readonly sortChangeEvent?: Observable<Sort>,) {

            super();
            this.valuesSubject = new ReplaySubject<readonly GenericPageViewDataRepresentation[]>(1);
            if(this.pageChangeEvent && this.sortChangeEvent) {
                this.pageChangeEvent.subscribe(pageEvent => this.onPageChangeEvent(pageEvent));
                this.sortChangeEvent.subscribe(sortEvent => this.onSortChangeEvent(sortEvent));
            } else {
                this.fetchAllPages().then(allPages => this.valuesSubject.next(allPages));
            }
        }

    connect(collectionViewer: CollectionViewer): Observable<readonly GenericPageViewDataRepresentation[]> {
        return this.valuesSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
    }

    private fetchPage(sortedPageParam: SortedPageParam): Observable<GenericPageViewDataCollectionRepresentation> {
        return this.pagedValuesProvider(sortedPageParam);
    }

    private async fetchAllPages(): Promise<readonly GenericPageViewDataRepresentation[]> {

        const firstPage = await firstValueFrom(this.fetchPage({
            pageParams: {
                page: GenericPageViewDataSource.DEFAULT_PAGE_INDEX,
                size: GenericPageViewDataSource.DEFAULT_PAGE_SIZE,
            },
            sort: {},
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
                    sort: {},
                }).pipe(
                    first(),
                )
            );
        }

        return (await firstValueFrom(zip(..._observables)))
            .flatMap(results => results.resources);
    }

    private onPageChangeEvent(pageEvent: PageEvent): void {
        this.page = pageEvent;
    }

    private onSortChangeEvent(sortEvent: Sort): void {
        this.sort = sortEvent;
    }

    private refreshPage() {

        const _sort: {[key: string]: 'ASC' | 'DESC'} = {};
        if(this.sort) {
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            _sort[this.sort.active] = (this.sort?.direction.toUpperCase() || 'ASC');
        }

        this.fetchPage({
                pageParams: {
                    page: this.page?.pageIndex,
                    size: this.page?.pageSize,
                },
                sort: _sort,
            })
            .pipe(map(v => v.resources))
            .subscribe(this.valuesSubject.next);
    }
}