import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddressInputComponent} from './address-input.component';
import {HttpClientTestingModule} from '@angular/common/http/testing';
import {HttpClient} from "@angular/common/http";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

describe('AddressInputComponent', () => {
  let component: AddressInputComponent;
  let mockedHttpClient: HttpClient;
  let fixture: ComponentFixture<AddressInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddressInputComponent],
      imports: [HttpClientTestingModule, FormsModule, ReactiveFormsModule, MatAutocompleteModule, MatInputModule,BrowserAnimationsModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddressInputComponent);
    component = fixture.componentInstance;
    component.addressForm  = new FormGroup({
        fullAddress: new FormControl('', Validators.required),
        country: new FormControl('', Validators.required),
        city: new FormControl('', Validators.required),
        longitude: new FormControl('', Validators.required),
        latitude: new FormControl('', Validators.required)
    });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('onSelected method', () => {
    // TODO: Ajouter longitude et latitude au context
    it('should fill address form fullAddress when selected address is not empty', () => {
      // given
      component.selectedAddress = {
        context: [{id: "country.8781", short_code: "fr", wikidata: "Q142", text: "France"}
          , {id: "place.26855501", mapbox_id: "dXJuOm1ieHBsYzpBWm5JVFE", text: "Béziers", wikidata: "Q174019"}],
        center: ['', ''],
        place_name: '3 rue tolbiac 75013 Paris France'
      };
      // when
      component.onSelected();
      // then
      expect(component.addressForm.controls.fullAddress.value).toEqual('3 rue tolbiac 75013 Paris France');
    });

    it('should fill country when selected address is not empty', () => {
      // given
      component.selectedAddress = {
        context: [{id: "country.8781", short_code: "fr", wikidata: "Q142", text: "France"}
          , {id: "place.26855501", mapbox_id: "dXJuOm1ieHBsYzpBWm5JVFE", text: "Béziers", wikidata: "Q174019"}],
        center: ['', ''],
        place_name: '3 rue tolbiac 75013 Paris France'
      };
      // when
      component.onSelected();
      // then
      expect(component.addressForm.controls.country.value).toEqual('France');
    });

    it('should fill longitude and latitude when selected address is not empty', () => {
      // given
      component.selectedAddress = {
        context: [{id: "country.8781", short_code: "fr", wikidata: "Q142", text: "France"}
          , {id: "place.26855501", mapbox_id: "dXJuOm1ieHBsYzpBWm5JVFE", text: "Paris", wikidata: "Q174019"}],
        center: ['4658.56', '21325.12'],
        place_name: '3 rue tolbiac 75013 Paris France'
      };
      // when
      component.onSelected();
      // then
      expect(component.addressForm.controls.longitude.value).toEqual('4658.56');
      expect(component.addressForm.controls.latitude.value).toEqual('21325.12');
    });
  });
});

