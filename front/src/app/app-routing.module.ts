import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {path: '', redirectTo: 'conference', pathMatch : 'full' },
  {path: 'conference', loadChildren: () => import('./conference/conference.module').then(m => m.ConferenceModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
