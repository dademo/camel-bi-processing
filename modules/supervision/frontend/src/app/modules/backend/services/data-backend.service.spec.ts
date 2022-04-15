import { TestBed } from '@angular/core/testing';

import { DataBackendService } from './data-backend.service';

describe('DataBackendService', () => {
  let service: DataBackendService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DataBackendService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
