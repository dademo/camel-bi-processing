import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Sort } from '@angular/material/sort';
import { Subject } from 'rxjs';
import { PagedValuesProvider } from '../data-model';
import { GenericPageViewDataSource } from '../generic-page-view-data-source';

@Component({
  selector: 'app-generic-page-view-display-list',
  templateUrl: './generic-page-view-display-list.component.html',
  styleUrls: ['./generic-page-view-display-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayListComponent implements OnInit {

  public readonly pageSizeOptions: number[] = [10, 25, 100];

  public readonly displayedColumns: string[] = [
      'name',
      'mainLink',
  ]

  public get attributes(): {[key: string]: string} {
    return {};
  }

  public dataSource: GenericPageViewDataSource | undefined;

  public length: number | undefined;
  public pageSize: number | undefined;

  @Input('pagedValuesProvider')
  public pagedValuesProvider: PagedValuesProvider | undefined;

  private readonly pageChangeEvent: Subject<PageEvent>;
  private readonly sortChangeEvent: Subject<Sort>;

  constructor() {

    this.pageChangeEvent = new Subject<PageEvent>();
    this.sortChangeEvent = new Subject<Sort>();
  }

  ngOnInit(): void {

    if(!Boolean(this.pagedValuesProvider)) {
      throw new Error('GenericPageViewDisplayListComponent: [this.pagedValuesProvider] must be defined');
    }
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    this.dataSource = new GenericPageViewDataSource(this.pagedValuesProvider, this.pageChangeEvent.asObservable(), this.sortChangeEvent.asObservable());
  }

  public onSortChange(sortEvent: Sort) {

  }

  public onPageChange(pageEvent: PageEvent): void {
    // TODO
  }

}
