import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppThemeComponent } from './app-theme.component';

describe('AppThemeComponent', () => {
  let component: AppThemeComponent;
  let fixture: ComponentFixture<AppThemeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppThemeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppThemeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
