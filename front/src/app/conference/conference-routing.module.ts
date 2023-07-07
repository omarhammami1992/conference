import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {CreateConferencePageComponent} from './pages/create-conference-page/create-conference-page.component';
import {HomeConferencePageComponent} from './pages/home-conference-page/home-conference-page.component';
import {MapConferencePageComponent} from './pages/map-conference-page/map-conference-page.component';


const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'home', component: HomeConferencePageComponent},
    {path: 'create', component: CreateConferencePageComponent},
    {path: 'map', component: MapConferencePageComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ConferenceRoutingModule {
}
