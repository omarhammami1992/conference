import {NgModule} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {ConferenceFormComponent} from './component/conference-form/conference-form.component';
import {ConferenceRoutingModule} from "./conference-routing.module";
import {CreateConferencePageComponent} from './pages/create-conference-page/create-conference-page.component';
import {HomeConferencePageComponent} from './pages/home-conference-page/home-conference-page.component';
import {ReactiveFormsModule} from '@angular/forms';
import {EarlyBirdPricingModeComponent} from './component/early-bird-pricing-mode/early-bird-pricing-mode.component';
import {GroupPricingModeComponent} from './component/group-pricing-mode/group-pricing-mode.component';
import {
  AttendingDaysPricingModeComponent
} from './component/attending-days-pricing-mode/attending-days-pricing-mode.component';
import {MapConferencePageComponent} from './pages/map-conference-page/map-conference-page.component';
import {ConferenceMapComponent} from './component/conference-map/conference-map.component';

import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from '@angular/material/input';
import {AddressInputComponent} from './component/address-input/address-input.component';

@NgModule({
  declarations: [
    CreateConferencePageComponent,
    HomeConferencePageComponent,
    ConferenceFormComponent,
    EarlyBirdPricingModeComponent,
    GroupPricingModeComponent,
    AttendingDaysPricingModeComponent,
    MapConferencePageComponent,
    ConferenceMapComponent,
    AttendingDaysPricingModeComponent,
    AddressInputComponent
  ],
  imports: [
    CommonModule,
    ConferenceRoutingModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [DatePipe]
})
export class ConferenceModule {
}
