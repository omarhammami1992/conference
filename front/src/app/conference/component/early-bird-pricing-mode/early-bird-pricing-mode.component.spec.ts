import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EarlyBirdPricingModeComponent } from './early-bird-pricing-mode.component';

describe('EarlyBirdPricingModeComponent', () => {
  let component: EarlyBirdPricingModeComponent;
  let fixture: ComponentFixture<EarlyBirdPricingModeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EarlyBirdPricingModeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EarlyBirdPricingModeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
