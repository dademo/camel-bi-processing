import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericPageViewDisplayListComponent } from './generic-page-view-display-list.component';

describe('GenericPageViewDisplayListComponent', () => {
  let component: GenericPageViewDisplayListComponent;
  let fixture: ComponentFixture<GenericPageViewDisplayListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericPageViewDisplayListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericPageViewDisplayListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
