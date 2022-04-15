import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppGuardComponent } from './app-guard.component';

describe('AppGuardComponent', () => {
  let component: AppGuardComponent;
  let fixture: ComponentFixture<AppGuardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppGuardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppGuardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
