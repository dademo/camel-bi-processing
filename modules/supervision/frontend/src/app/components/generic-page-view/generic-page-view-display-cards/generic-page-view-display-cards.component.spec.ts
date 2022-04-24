import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericPageViewDisplayCardsComponent } from './generic-page-view-display-cards.component';

describe('GenericPageViewDisplayCardsComponent', () => {
  let component: GenericPageViewDisplayCardsComponent;
  let fixture: ComponentFixture<GenericPageViewDisplayCardsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericPageViewDisplayCardsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericPageViewDisplayCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
