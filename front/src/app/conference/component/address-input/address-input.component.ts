import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {debounceTime, distinctUntilChanged, filter, finalize, switchMap, tap} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-address-input',
  templateUrl: './address-input.component.html',
  styleUrls: ['./address-input.component.scss']
})
export class AddressInputComponent implements OnInit {
  selectedAddress: any = '';
  searchAddressControl = new FormControl();
  @Input() addressForm = new FormGroup({});
  filteredAddress: any;
  isLoading = false;
  errorMsg!: string;
  minLengthTerm = 3;

  constructor(private http: HttpClient, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.searchAddressControl.valueChanges
      .pipe(
        filter(res =>
          res !== null && res.length >= this.minLengthTerm
        ),
        distinctUntilChanged(),
        debounceTime(500),
        tap(() => {
          this.errorMsg = '';
          this.filteredAddress = [];
          this.isLoading = true;
        }),
        switchMap(value => this.http.get('https://api.mapbox.com/geocoding/v5/mapbox.places/' + value + '.json?access_token=' + environment.mapbox.accessToken)
          .pipe(
            finalize(() => {
              this.isLoading = false;
            })
          )
        )
      )
      .subscribe((data: any) => {
        this.filteredAddress = data.features;
      });
  }

  onSelected() {
    const country = this.selectedAddress.context
      .find((context: any) => context.id.startsWith('country'))
      .text;

    const city = this.selectedAddress.context
      .find((context: any) => context.id.startsWith('place'))
      .text;
    // TODO : check if longitude and latitude are not null
    let [longitude, latitude] = this.selectedAddress.center;

    let fullAddress = this.selectedAddress.place_name;
    const addressData = {country, city, longitude, latitude, fullAddress}
    this.addressForm.patchValue(addressData);
  }

  clearSelection() {
    this.selectedAddress = "";
    this.filteredAddress = [];
  }

  displayWith(value:any) {
    return value?.place_name;
  }

  markAddressFormTouched() {
    this.addressForm.markAllAsTouched();
  }

}
