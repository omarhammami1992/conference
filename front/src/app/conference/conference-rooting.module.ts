import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {CreateConferencePageComponent} from "./create-conference-page/create-conference-page.component";
import {ConferenceComponent} from "./conference.component";

const routes: Routes = [
  {
    path: 'conference',
    component: ConferenceComponent,
    children: [
      {path: 'create', component: CreateConferencePageComponent},
      {path: '', redirectTo: 'create', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConferenceRootingModule {
}
