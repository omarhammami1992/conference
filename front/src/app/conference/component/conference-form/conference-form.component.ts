import {Component, OnInit} from '@angular/core';
import {ConferenceService} from '../../service/conference.service';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {Conference} from '../../model/conference';

@Component({
  selector: 'app-conference-form',
  templateUrl: './conference-form.component.html',
  styleUrls: ['./conference-form.component.scss']
})
export class ConferenceFormComponent implements OnInit {

  pricingModes = [
    {
      value: '',
      label: ''
    },
    {
      value: 'earlyBird',
      label: 'Early bird'
    },
    {
      value: 'group',
      label: 'Group'
    },
    {
      value: 'attendingDays',
      label: 'Attending days'
    }
  ];
  conferenceForm: FormGroup = new FormGroup({});

  dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const start = control.get('startDate');
    const end = control.get('endDate');
    return start?.value !== null && end?.value !== null && start?.value < end?.value
      ? null : {dateValid: true};
  }


  constructor(private _conferenceService: ConferenceService, private _formBuilder: FormBuilder) {

  }


  ngOnInit(): void {
    this.conferenceForm = this._formBuilder.group({
      name: ['', Validators.required],
      link: ['', [Validators.required, Validators.pattern("^(http[s]?:\\/\\/){0,1}(www\\.){0,1}[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,5}[\\.]{0,1}[\\w\\/]*")]],
      price: ['', [Validators.required, Validators.min(1)]],
      startDate: ['', [Validators.required,]],
      endDate: ['', [Validators.required]],
      city: ['', Validators.required],
      country: ['', Validators.required],
      pricingMode:['', [Validators.required]],
      earlyBirdForm: this._formBuilder.group({
        ranges: this._formBuilder.array([])
      })
    }, {validators: this.dateValidator});
  }

  get earlyBirdForm(): FormGroup {
    return this.conferenceForm.controls.earlyBirdForm as FormGroup;
  }

  createConference() {
    if (this.conferenceForm.invalid) {
      console.log(this.conferenceForm.errors);
      this.conferenceForm.markAllAsTouched()
      return;
    }
    const conference: Conference = {
      name: this.conferenceForm.controls.name.value,
      price: Number.parseFloat(this.conferenceForm.controls.price.value),
      link: this.conferenceForm.controls.link.value,
      city: this.conferenceForm.controls.city.value,
      country: this.conferenceForm.controls.country.value,
      startDate: new Date(this.conferenceForm.controls.startDate.value),
      endDate: new Date(this.conferenceForm.controls.endDate.value)
    }
    console.log(this.conferenceForm.controls.earlyBirdForm.value);

    this._conferenceService.createConference(conference);
  }
}
