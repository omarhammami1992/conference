import { TestBed } from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import { ConferenceService } from './conference.service';
import {Conference} from '../model/conference';
import {HttpClient} from '@angular/common/http';
import {of, Observable} from 'rxjs';

describe('ConferenceService', () => {
  let service: ConferenceService;
  let mockedHttpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({imports: [HttpClientTestingModule]});
    service = TestBed.inject(ConferenceService);

    // Inject the http service and test controller for each test
    mockedHttpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('Conference Management', () => {
    it('should call /api/conference and return saved conference id', () => {
      // given
      const  conference = {} as Conference;
      let expectedResult = of(123);
      spyOn(mockedHttpClient,'post').and.returnValue(expectedResult)

      // when
      const savedConferenceId = service.createConference(conference);

      // then
      expect(mockedHttpClient.post).toHaveBeenCalledWith('/api/conference', conference)
      expect(savedConferenceId).toEqual(expectedResult)
    });
  });

  it('should call /api/conference and return conference by id', () => {
    // given
    const idConference = 123;
    const expectedResult : Observable<Conference> = of({
      id: 123,
      name: "Name",
      price: 1000,
      link: "archi hexa",
      city:"Paris",
      country:"France",
      startDate: new Date("2022-01-01"),
      endDate: new Date("2022-01-03")
    } as Conference);
    spyOn(mockedHttpClient,'get').and.returnValue(expectedResult);

    // when
    const conferenceDetail = service.getConferenceDetailById(idConference);

    // then
    expect(mockedHttpClient.get).toHaveBeenCalledWith('/api/conference/123');
    expect(conferenceDetail).toEqual(expectedResult);
  });

});
