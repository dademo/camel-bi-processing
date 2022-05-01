import { TestBed } from '@angular/core/testing';

import { GenericPageViewDataSource } from './generic-page-view-data-source';

describe('GenericPageViewDataSource', () => {
  let dataSource: GenericPageViewDataSource;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    dataSource = TestBed.inject(GenericPageViewDataSource);
  });

  it('should be created', () => {
    expect(dataSource).toBeTruthy();
  });
});
