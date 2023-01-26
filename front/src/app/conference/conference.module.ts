import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConferenceFormComponent } from './component/conference-form/conference-form.component';
import {ConferenceRoutingModule} from "./conference-routing.module";
import { CreateConferencePageComponent } from './pages/create-conference-page/create-conference-page.component';
import { HomeConferencePageComponent } from './pages/home-conference-page/home-conference-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { EarlyBirdPricingModeComponent } from './component/early-bird-pricing-mode/early-bird-pricing-mode.component';
import { GroupPricingModeComponent } from './component/group-pricing-mode/group-pricing-mode.component';
import { AttendingDaysPricingModeComponent } from './component/attending-days-pricing-mode/attending-days-pricing-mode.component';


@NgModule({
  declarations: [
    CreateConferencePageComponent,
    HomeConferencePageComponent,
    ConferenceFormComponent,
    EarlyBirdPricingModeComponent,
    GroupPricingModeComponent,
    AttendingDaysPricingModeComponent
  ],
  imports: [
    CommonModule,
    ConferenceRoutingModule,
    ReactiveFormsModule
  ]
})
export class ConferenceModule { }
