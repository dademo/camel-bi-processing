import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DataBackendsComponent } from './data-backends.component';

describe('DataBackendsComponent', () => {
  let component: DataBackendsComponent;
  let fixture: ComponentFixture<DataBackendsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DataBackendsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DataBackendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
