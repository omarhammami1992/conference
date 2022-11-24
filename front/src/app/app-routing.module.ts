import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ConferenceModule} from "./conference/conference.module";

const routes: Routes = [
  {path: 'conference', loadChildren: () => import('./conference/conference.module').then(m => m.ConferenceModule) },
  {path: '', redirectTo: 'conference',pathMatch : 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), ConferenceModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
