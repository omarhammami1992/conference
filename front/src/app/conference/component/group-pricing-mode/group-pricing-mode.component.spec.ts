import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupPricingModeComponent } from './group-pricing-mode.component';

describe('GroupPricingModeComponent', () => {
  let component: GroupPricingModeComponent;
  let fixture: ComponentFixture<GroupPricingModeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GroupPricingModeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupPricingModeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
