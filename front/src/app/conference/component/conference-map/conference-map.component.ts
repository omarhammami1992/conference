import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import { Location } from '../../model/location';
import { LightConference } from '../../model/light-conference';

const INITIAL_ZOOM : number = 10;

@Component({
  selector: 'app-conference-map',
  templateUrl: './conference-map.component.html',
  styleUrls: ['./conference-map.component.scss']
})
export class ConferenceMapComponent implements AfterViewInit {

  //TODO: private access modifier ??
  map: any;
  LightConferences : LightConference[];

  constructor() {
   this.LightConferences = [];
  }


  private initMap(location : Location, initialZoom : number): void {

   this.map = L.map('map', {
      center: [ location.lat , location.long ],
      zoom: initialZoom
   });

    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(this.map);

    var myIcon = L.icon({
      iconUrl: 'assets/marker-icon-2x.png',
      iconSize: [25, 41],
      iconAnchor: [13, 40],
      popupAnchor: [-3, -76],
      shadowUrl: 'assets/marker-shadow.png',
      shadowSize: [60, 41],
      shadowAnchor: [18, 40]
    });


           this.LightConferences = [
           {
              name: "Super dummy Conf XX",
              price: 1000,
              startDate: new Date("2022-01-01"),
              location: {lat:48.8300562,long:2.3734685}
            },
            {
              name: "Boring stuff Conf :s",
              price: 1000,
              startDate: new Date("2022-01-01"),
              location: {lat:48.88519155689607,long:2.313205134143403}
             },
            {
              name: "Marvel not cool conference",
              price: 1000,
              startDate: new Date("2022-01-01"),
              location: {lat:48.81017091550302,long:2.306230060272181}
            }
            ];



      this.LightConferences.forEach ( lightConference => {

       L.marker([lightConference.location.lat,lightConference.location.long], {icon: myIcon})
          .bindPopup(`<b>${lightConference.name}</b><br>
                      Price: ${lightConference.price}€<br>
                      Start date:  ${lightConference.startDate}`
          ,{offset:[3,43]})
          .openPopup()
          .addTo(this.map);
      });

   }

   ngAfterViewInit(): void {
     let location : Location = {lat:48.8300562,long:2.3734685};
     this.initMap(location,INITIAL_ZOOM);
   }

}
