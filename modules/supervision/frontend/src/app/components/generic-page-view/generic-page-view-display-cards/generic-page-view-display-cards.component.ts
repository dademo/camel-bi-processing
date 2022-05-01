import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef, Input } from '@angular/core';
import { PagedValuesProvider } from '../data-model';

@Component({
  selector: 'app-generic-page-view-display-cards',
  templateUrl: './generic-page-view-display-cards.component.html',
  styleUrls: ['./generic-page-view-display-cards.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayCardsComponent implements OnInit {

  @Input('pagedValuesProvider')
  public pagedValuesProvider: PagedValuesProvider | undefined;

  constructor() { }

  ngOnInit(): void {

    if(!Boolean(this.pagedValuesProvider)) {
      throw new Error('GenericPageViewDisplayCardsComponent: [this.pagedValuesProvider] must be defined');
    }
  }

}
