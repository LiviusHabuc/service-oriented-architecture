import { TestBed } from '@angular/core/testing';

import { DisplayCardsService } from './display-cards.service';

describe('DisplayCardsService', () => {
  let service: DisplayCardsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DisplayCardsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
