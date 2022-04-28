import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-generic-page-view-display-cards',
  templateUrl: './generic-page-view-display-cards.component.html',
  styleUrls: ['./generic-page-view-display-cards.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayCardsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
