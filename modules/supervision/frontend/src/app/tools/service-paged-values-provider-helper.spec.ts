import { TestBed } from '@angular/core/testing';

import { ServicePagedValuesProviderHelper } from './service-paged-values-provider-helper';

describe('ServicePagedValuesProviderHelperService', () => {
  let service: ServicePagedValuesProviderHelper;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicePagedValuesProviderHelper);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
