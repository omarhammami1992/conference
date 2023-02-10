import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EarlyBirdPricingModeComponent } from './early-bird-pricing-mode.component';
import {By} from "@angular/platform-browser";
import {ReactiveFormsModule} from "@angular/forms";

describe('EarlyBirdPricingModeComponent', () => {
  let component: EarlyBirdPricingModeComponent;
  let fixture: ComponentFixture<EarlyBirdPricingModeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EarlyBirdPricingModeComponent ],
      imports: [ReactiveFormsModule]
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

    it('should not contains delete button on first price range form', () => {
      //when
      const deleteButton = fixture.debugElement.query(By.css('#delete-btn-0'));

      //then
      expect(deleteButton).toBeNull()
    })

    it('should add price range form when clicking on add button', () => {
      // when
      const addPriceRangeButtonInput = fixture.debugElement.query(By.css('#add-price-range-btn'));
      addPriceRangeButtonInput.nativeElement.click();
      fixture.detectChanges();

      const priceRangeForms = fixture.debugElement.queryAll(By.css('.price-range-form'));
      expect(priceRangeForms.length).toEqual(2)
    });

    it('should delete price range when clicking on delete button', () => {
      //given
      component.addPriceRangeForm();
      fixture.detectChanges();

      //when
      const deleteButton = fixture.debugElement.query(By.css('#delete-btn-1'));
      deleteButton.nativeElement.click();
      fixture.detectChanges();

      //then
      expect(component.ranges.length).toEqual(1)
    })
  });

});
