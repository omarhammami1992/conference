import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-early-bird-pricing-mode',
  templateUrl: './early-bird-pricing-mode.component.html',
  styleUrls: ['./early-bird-pricing-mode.component.scss']
})
export class EarlyBirdPricingModeComponent implements OnInit {

  formGroup: FormGroup = new FormGroup({});

  constructor(private _formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.formGroup = this._formBuilder.group({ranges: this._formBuilder.array([])});
  }

}
