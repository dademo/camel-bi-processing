import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenericPageViewRouteComponent } from './generic-page-view-route.component';

describe('GenericPageViewRouteComponent', () => {
  let component: GenericPageViewRouteComponent;
  let fixture: ComponentFixture<GenericPageViewRouteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GenericPageViewRouteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GenericPageViewRouteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
