import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConferenceFormComponent } from './component/conference-form/conference-form.component';
import {ConferenceRootingModule} from "./conference-rooting.module";
import { CreateConferencePageComponent } from './pages/create-conference-page/create-conference-page.component';
import { HomeConferencePageComponent } from './pages/home-conference-page/home-conference-page.component';


@NgModule({
  declarations: [
    CreateConferencePageComponent,
    HomeConferencePageComponent,
    ConferenceFormComponent
  ],
  imports: [
    CommonModule,
    ConferenceRootingModule
  ]
})
export class ConferenceModule { }
