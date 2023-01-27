import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendingDaysPricingModeComponent } from './attending-days-pricing-mode.component';
import {By} from "@angular/platform-browser";

describe('AttendingDaysPricingModeComponent', () => {
  let component: AttendingDaysPricingModeComponent;
  let fixture: ComponentFixture<AttendingDaysPricingModeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttendingDaysPricingModeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AttendingDaysPricingModeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
