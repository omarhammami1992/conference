import { Component, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';
import { LongLat } from '../../model/LongLat';

@Component({
  selector: 'app-conference-map',
  templateUrl: './conference-map.component.html',
  styleUrls: ['./conference-map.component.scss']
})
export class ConferenceMapComponent implements AfterViewInit {

  //TODO: private access modifier ??
  map: any;

  constructor() {
  }


  private initMap(longLat : LongLat): void {

   this.map = L.map('map', {
      center: [ 48.8300562,2.3734685 ],
      zoom: 10
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

    L.marker([48.8300562,2.3734685], {icon: myIcon})
    .bindPopup("<b>Devoxx Paris</b><br>Java.",{offset:[3,43]})
    .openPopup()
    .addTo(this.map);

    L.marker([48.88519155689607, 2.313205134143403], {icon: myIcon}).addTo(this.map);
    L.marker([48.81017091550302, 2.306230060272181], {icon: myIcon}).addTo(this.map);
   }

   ngAfterViewInit(): void {
     this.initMap();
   }

}
