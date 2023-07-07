import {ComponentFixture, TestBed} from '@angular/core/testing';
import {of} from 'rxjs';
import {LightConference} from '../../model/light-conference';
import {ConferenceService} from '../../service/conference.service';
import {MapConferencePageComponent} from './map-conference-page.component';
import {MarkerOptions} from '../../model/map-display-information';
import {DatePipe} from "@angular/common";
import SpyObj = jasmine.SpyObj;
import {ConferenceMapComponent} from "../../component/conference-map/conference-map.component";
import {CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";

describe('MapConferencePageComponent', () => {
  let component: MapConferencePageComponent;
  let fixture: ComponentFixture<MapConferencePageComponent>;
  let mockConferenceService: SpyObj<ConferenceService>;


  beforeEach(async () => {
    mockConferenceService = jasmine.createSpyObj(ConferenceService, ["getLightConferences"]);
    await TestBed.configureTestingModule({
      declarations: [MapConferencePageComponent, ConferenceMapComponent],
      providers: [
        {provide: ConferenceService, useValue: mockConferenceService},
        {provide: DatePipe, useValue: new DatePipe('en')}
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapConferencePageComponent);
    component = fixture.componentInstance;
    mockConferenceService.getLightConferences.and.returnValue(of([]));
  });

  afterEach(() => {
    mockConferenceService.getLightConferences.calls.reset();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('get list of light conferences', () => {

    const lightConferences: LightConference[] = [
      {
        name: "conference 1",
        address: {
          latitude: 15,
          longitude: 8
        },
        fullPrice: 0,
        startDate: new Date(2023,7,15)
      }
    ]

    mockConferenceService.getLightConferences.and.returnValue(of(lightConferences));
    fixture.detectChanges();
    const expectedMarker: MarkerOptions =
        {
          position: {
            latitude: 15,
            longitude: 8
          },
          popupContent: `<b>conference 1</b><br>
                        Price: 0€<br>
                        Start date: 15-08-2023`
        }
    expect(component.markerOptionsList).toContain(expectedMarker);
  });

  it('should have one marker options when multiple conferences have same address', () => {
    const lightConferences: LightConference[] = [
      {
        name: "conference 1",
        address: {
          latitude: 15,
          longitude: 8
        },
        fullPrice: 0,
        startDate: new Date(2023,7,15)
      },
      {
        name: "conference 2",
        address: {
          latitude: 15,
          longitude: 8
        },
        fullPrice: 10,
        startDate: new Date(2023,8,15)
      }
    ]

    mockConferenceService.getLightConferences.and.returnValue(of(lightConferences));
    fixture.detectChanges();
    const expectedMarker: MarkerOptions =
      {
        position: {
          latitude: 15,
          longitude: 8
        },
        popupContent: `<b>conference 1</b><br>
                        Price: 0€<br>
                        Start date: 15-08-2023<br><hr><b>conference 2</b><br>
                        Price: 10€<br>
                        Start date: 15-09-2023`
      }
    expect(component.markerOptionsList).toContain(expectedMarker);
  });



});
