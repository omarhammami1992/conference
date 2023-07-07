import {AfterViewInit, Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import * as L from 'leaflet';
import {Address} from '../../model/address';
import {MarkerOptions} from "../../model/map-display-information";

const INITIAL_ZOOM: number = 10;


@Component({
  selector: 'app-conference-map',
  templateUrl: './conference-map.component.html',
  styleUrls: ['./conference-map.component.scss']
})
export class ConferenceMapComponent implements OnChanges, AfterViewInit {

  @Input() markerOptionsList: MarkerOptions[] = [];
  map: any;

  private markerIcon = L.icon({
    iconUrl: 'assets/marker-icon-2x.png',
    iconSize: [25, 41],
    iconAnchor: [13, 40],
    popupAnchor: [-3, -76],
    shadowUrl: 'assets/marker-shadow.png',
    shadowSize: [60, 41],
    shadowAnchor: [18, 40]
  });

  constructor() {
  }

  private initMap(location: Address, initialZoom: number): void {
    this.map = L.map('map', {
      center: [location.latitude, location.longitude],
      zoom: initialZoom
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(this.map);

  }

  private displayMarkers() {
    this.markerOptionsList.forEach(markerOptions => {
      L.marker([markerOptions.position.latitude, markerOptions.position.longitude], {icon: this.markerIcon})
        .bindPopup(markerOptions.popupContent
          , {offset: [3, 43]})
        .openPopup()
        .addTo(this.map);
    });
  }

  ngAfterViewInit(): void {
    let location: Address = {latitude: 48.8300562, longitude: 2.3734685};
    this.initMap(location, INITIAL_ZOOM);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(!changes['markerOptionsList'].isFirstChange()) {
      this.displayMarkers();
    }
  }

}
