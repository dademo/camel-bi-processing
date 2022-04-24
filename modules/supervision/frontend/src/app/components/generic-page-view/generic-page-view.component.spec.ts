import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericPageViewComponent } from './generic-page-view.component';

describe('GenericPageViewComponent', () => {
  let component: GenericPageViewComponent;
  let fixture: ComponentFixture<GenericPageViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericPageViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericPageViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
