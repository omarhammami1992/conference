import {Component, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-early-bird-pricing-mode',
  templateUrl: './early-bird-pricing-mode.component.html',
  styleUrls: ['./early-bird-pricing-mode.component.scss']
})
export class EarlyBirdPricingModeComponent implements OnInit {

  @Input() formGroup: FormGroup = new FormGroup({});

  constructor(private _formBuilder: FormBuilder) {
  }
  ngOnInit(): void {
    this.addPriceRangeForm();
  }

  get ranges() {
    return this.formGroup.controls['ranges'] as FormArray;
  }

  addPriceRangeForm() {
    const priceRangeForm = this._formBuilder.group({
      startDate: [''],
      endDate: [''],
      price: [''],
    });
    this.ranges.push(priceRangeForm);
  }

  deletePriceRangeForm(index: number) {
    this.ranges.removeAt(index);
  }
}
