import { TestBed, inject } from '@angular/core/testing';

import { SoundboardService } from './soundboard.service';

describe('SoundboardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SoundboardService]
    });
  });

  it('should be created', inject([SoundboardService], (service: SoundboardService) => {
    expect(service).toBeTruthy();
  }));
});
