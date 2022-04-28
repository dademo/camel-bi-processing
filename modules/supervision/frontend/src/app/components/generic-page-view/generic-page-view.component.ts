import { Component, ComponentRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { ViewRoute } from './data-model';
import { GenericPageViewDisplayCardsComponent } from './generic-page-view-display-cards/generic-page-view-display-cards.component';
import { GenericPageViewDisplayListComponent } from './generic-page-view-display-list/generic-page-view-display-list.component';

type ViewType = 'list' | 'card';

@Component({
  selector: 'app-generic-page-view',
  templateUrl: './generic-page-view.component.html',
  styleUrls: ['./generic-page-view.component.scss']
})
export class GenericPageViewComponent implements OnInit, OnChanges {

  public displayKind: ViewType = 'list';

  // TODO
  @Input()
  public values: Array<object> | undefined;

  @Input()
  public viewRoutes: Array<ViewRoute> | undefined;

  @Input()
  public valuesChanged: boolean = false;

  @Output()
  public readonly valuesChangedChange: EventEmitter<boolean>;

  @ViewChild(GenericPageViewDisplayListComponent)
  public displayListViewRef: ComponentRef<any> | undefined;

  @ViewChild(GenericPageViewDisplayCardsComponent)
  public displayCardsViewRef: ComponentRef<any> | undefined;


  constructor() {
    this.valuesChangedChange = new EventEmitter<boolean>();
  }

  ngOnInit(): void {
    
  }

  ngOnChanges(changes: SimpleChanges): void {
    
    if(changes['valuesChanged']?.currentValue === true) {
      this.onValuesChanged();
    }
  }

  private onValuesChanged(): void {
    
  }

}
