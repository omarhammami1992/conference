import {Component, OnInit} from '@angular/core';
import {ConferenceService} from '../../service/conference.service';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from '@angular/forms';
import {Conference} from '../../model/conference';
import {debounceTime, distinctUntilChanged, filter, finalize, switchMap, tap} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";

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

  selectedAddress: any = "";
  searchAddressControl = new FormControl();
  filteredAddress: any;
  isLoading = false;
  errorMsg!: string;
  minLengthTerm = 3;

  dateValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const start = control.get('startDate');
    const end = control.get('endDate');
    return start?.value !== null && end?.value !== null && start?.value < end?.value
      ? null : {dateValid: true};
  }


  constructor(private _conferenceService: ConferenceService, private _formBuilder: FormBuilder, private http: HttpClient) {

  }


  ngOnInit(): void {
    this.conferenceForm = this._formBuilder.group({
      name: ['', Validators.required],
      link: ['', [Validators.required, Validators.pattern("^(http[s]?:\\/\\/){0,1}(www\\.){0,1}[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,5}[\\.]{0,1}[\\w\\/]*")]],
      price: ['', [Validators.required, Validators.min(1)]],
      startDate: ['', [Validators.required,]],
      endDate: ['', [Validators.required]],
      addressForm: this._formBuilder.group({
        city: ['', Validators.required],
        country: ['', Validators.required],
        longitude: ['', Validators.required],
        latitude: ['', Validators.required],
        fullAddress: ['', Validators.required]
      }),
      pricingMode:[''],
      earlyBirdForm: this._formBuilder.group({
        ranges: this._formBuilder.array([])
      })
    }, {validators: this.dateValidator});

    this.searchAddressControl.valueChanges
        .pipe(
            filter(res =>
               res !== null && res.length >= this.minLengthTerm
            ),
            distinctUntilChanged(),
            debounceTime(500),
            tap(() => {
              this.errorMsg = "";
              this.filteredAddress = [];
              this.isLoading = true;
            }),
            switchMap(value => this.http.get('https://api.mapbox.com/geocoding/v5/mapbox.places/' + value + '.json?access_token=' + environment.mapbox.accessToken)
                .pipe(
                    finalize(() => {
                      this.isLoading = false
                    }),
                )
            )
        )
        .subscribe((data: any) => {
          this.filteredAddress = data.features;
        });
  }



  get earlyBirdForm(): FormGroup {
    return this.conferenceForm.controls.earlyBirdForm as FormGroup;
  }

  get addressForm(): FormGroup {
    return this.conferenceForm.controls.addressForm as FormGroup;
  }

  createConference() {
    if (this.conferenceForm.invalid) {
      this.conferenceForm.markAllAsTouched()
      return;
    }
    const conference: Conference = {
      name: this.conferenceForm.controls.name.value,
      price: Number.parseFloat(this.conferenceForm.controls.price.value),
      link: this.conferenceForm.controls.link.value,
      address: this.conferenceForm.controls.addressForm.value,
      startDate: new Date(this.conferenceForm.controls.startDate.value),
      endDate: new Date(this.conferenceForm.controls.endDate.value),
      priceRanges: [],
      priceAttendingDays: []
    }

    this._conferenceService.createConference(conference).subscribe(
      response => {
        console.log("OK");
      }
    );
  }
}
