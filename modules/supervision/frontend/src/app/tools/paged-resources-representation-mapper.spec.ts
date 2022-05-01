import { TestBed } from '@angular/core/testing';

import { PagedResourcesRepresentationMapperService } from './paged-resources-representation-mapper';

describe('PagedResourcesRepresentationMapperService', () => {
  let service: PagedResourcesRepresentationMapperService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PagedResourcesRepresentationMapperService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
