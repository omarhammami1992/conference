import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConferenceFormComponent } from './component/conference-form/conference-form.component';
import {ConferenceRoutingModule} from "./conference-routing.module";
import { CreateConferencePageComponent } from './pages/create-conference-page/create-conference-page.component';
import { HomeConferencePageComponent } from './pages/home-conference-page/home-conference-page.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    CreateConferencePageComponent,
    HomeConferencePageComponent,
    ConferenceFormComponent
  ],
  imports: [
    CommonModule,
    ConferenceRoutingModule,
    ReactiveFormsModule
  ]
})
export class ConferenceModule { }
