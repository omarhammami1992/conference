import {AfterViewInit, Component, OnInit} from '@angular/core';
import {LightConference} from '../../model/light-conference';
import {ConferenceService} from '../../service/conference.service';
import {MarkerOptions} from '../../model/map-display-information';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-map-conference-page',
  templateUrl: './map-conference-page.component.html',
  styleUrls: ['./map-conference-page.component.scss']
})
export class MapConferencePageComponent implements OnInit, AfterViewInit {

  public lightConferences: LightConference[] = [];
  public markerOptionsList: MarkerOptions[] = [];


  constructor(private conferenceService: ConferenceService, private _datePipe: DatePipe) {

  }

  ngOnInit(): void {

    this.conferenceService.getLightConferences().subscribe((res) => {
      this.lightConferences = res;
      this.buildMarkerOptionsList(this.lightConferences);
    });
  }

  buildMarkerOptionsList(lightConferences: LightConference[]) {

    const markerInputsList = lightConferences
      .reduce((group:any, lightConference) => {
        const key = lightConference.address.latitude + '-' +  lightConference.address.longitude;
        if (!group[key]) {
          group[key] = [];
        }
        group[key].push(lightConference);

        return group;
      }, {});

    const markerOptionsList = [];

    for (const positionAsString in markerInputsList) {
      const conferences: LightConference[] = markerInputsList[positionAsString];
      const positionArray = positionAsString.split('-');

      const latitude: number = Number(positionArray[0]);
      const longitude: number = Number(positionArray[1]);

      const popupContent = conferences.map(conference => {
        return `<b>${conference.name}</b><br>
                        Price: ${conference.fullPrice}€<br>
                        Start date: ${this._datePipe.transform(conference.startDate, 'dd-MM-YYYY', "en-US")}`
      }).join('<br><hr>');

      const markerOptions: MarkerOptions = {
          position: {
            latitude,
            longitude
          },
          popupContent
        };
      markerOptionsList.push(markerOptions);
    }


    this.markerOptionsList = markerOptionsList;
  }


  ngAfterViewInit(): void {

  }

}
