import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-generic-page-view-display-list',
  templateUrl: './generic-page-view-display-list.component.html',
  styleUrls: ['./generic-page-view-display-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class GenericPageViewDisplayListComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
