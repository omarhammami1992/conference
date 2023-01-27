import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EarlyBirdPricingModeComponent } from './early-bird-pricing-mode.component';
import {By} from "@angular/platform-browser";

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

  describe('template', () => {
    it('should contain start date input', () => {
      // when
      const startDateInput = fixture.debugElement.query(By.css('.start-date'));

      // then
      expect(startDateInput).toBeTruthy();
    });

    it('should contain end date input', () => {
      // when
      const endDateInput = fixture.debugElement.query(By.css('.end-date'));

      // then
      expect(endDateInput).toBeTruthy();
    });

    it('should contain price input', () => {
      // when
      const priceInput = fixture.debugElement.query(By.css('.price'));

      // then
      expect(priceInput).toBeTruthy();
    });

    it('should contain add price button', () => {
      // when
      const addPriceRangeButtonInput = fixture.debugElement.query(By.css('#add-price-range-btn'));

      // then
      expect(addPriceRangeButtonInput).toBeTruthy();
    });

    it('when clicking on button should add price range form', () => {
      // when
      const addPriceRangeButtonInput = fixture.debugElement.query(By.css('#add-price-range-btn'));
      addPriceRangeButtonInput.nativeElement.click();
      fixture.detectChanges();

      const priceRangeForms = fixture.debugElement.queryAll(By.css('.price-range-form'));
      expect(priceRangeForms.length).toEqual(2)
    });
  });


});
