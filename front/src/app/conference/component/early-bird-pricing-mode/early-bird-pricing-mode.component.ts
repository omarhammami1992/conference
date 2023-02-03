import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-early-bird-pricing-mode',
  templateUrl: './early-bird-pricing-mode.component.html',
  styleUrls: ['./early-bird-pricing-mode.component.scss']
})
export class EarlyBirdPricingModeComponent implements OnInit {

  formGroup: FormGroup = new FormGroup({});
  //ranges: FormArray = new FormArray([]);

  constructor(private _formBuilder: FormBuilder) {
  }

  get ranges() {
    return this.formGroup.controls["ranges"] as FormArray;
  }

  ngOnInit(): void {
    // this.ranges = this._formBuilder.array([
    // ])
    this.formGroup = this._formBuilder.group({
      ranges: this._formBuilder.array([
      ])
    });

  }

  addPriceRangeForm() {
    const ranges = this.formGroup.controls.ranges as FormArray
    ranges.push(this._formBuilder.group({
      startDate: [''],
      endDate: [''],
      price: [''],
    }));
  }
}
