import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConferenceMapComponent} from './conference-map.component';
import {LightConference} from "../../model/light-conference";
import {MarkerOptions} from "../../model/map-display-information";

describe('ConferenceMapComponent', () => {
  let component: ConferenceMapComponent;
  let fixture: ComponentFixture<ConferenceMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConferenceMapComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConferenceMapComponent);
    component = fixture.componentInstance;
   });

  describe('with initialization', () => {
    beforeEach(() => {
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

  });

  describe('Template', () => {
    it('should contain geoloc icons', () => {
      let markerOptions: MarkerOptions[] = [
        {
          position: {
            latitude: 1,
            longitude: 2
          },
          popupContent: `<b>conference</b><br>
                        Price: 10€<br>
                        Start date: 14-07-2023`
        },
        {
          position: {
            latitude: 3,
            longitude: 4
          },
          popupContent: `<b>confeconference2</b><br>
                        Price: 12€<br>
                        Start date: 15-08-2023`
        }
      ]
      // mockConferenceService.getLightConferences.and.returnValue(of(lightConferences));
      component.markerOptionsList = markerOptions;

      fixture.detectChanges();

      // const markerIcons = fixture.nativeElement.querySelectorAll('')
    })
  })
});


