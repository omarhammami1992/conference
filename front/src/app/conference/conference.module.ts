import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CreateConferencePageComponent } from './create-conference-page/create-conference-page.component';
import { ConferenceFormComponent } from './component/conference-form/conference-form.component';
import {ConferenceRootingModule} from "./conference-rooting.module";



@NgModule({
  declarations: [
    CreateConferencePageComponent,
    ConferenceFormComponent
  ],
  imports: [
    CommonModule,
    ConferenceRootingModule
  ]
})
export class ConferenceModule { }
