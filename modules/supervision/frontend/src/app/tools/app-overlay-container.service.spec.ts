import { TestBed } from '@angular/core/testing';

import { AppOverlayContainer } from './app-overlay-container.service';

describe('AppOverlayContainerService', () => {
  let service: AppOverlayContainer;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppOverlayContainer);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
