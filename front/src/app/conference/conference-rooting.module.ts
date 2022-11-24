import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {CreateConferencePageComponent} from "./create-conference-page/create-conference-page.component";

const routes: Routes = [
  /*{
    path: '', children: [
      {path: 'create', component: CreateConferencePageComponent}
    ],
    redirectTo:'create/children',
    pathMatch: 'full'
  },*/
  {path: 'create', component: CreateConferencePageComponent},
  // {path: '', redirectTo: 'create', pathMatch: 'full'}


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConferenceRootingModule {
}
