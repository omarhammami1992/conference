import { TestBed } from '@angular/core/testing';

import { ConferenceService } from './conference.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Conference} from "../model/conference";
import {HttpErrorResponse} from "@angular/common/http";

describe('ConferenceService', () => {
  let service: ConferenceService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ConferenceService
      ]
    });
    service = TestBed.inject(ConferenceService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe("createConference", () => {
    it("should send post request to create conference and return new conference id", () => {
      const conference = {} as Conference;
      let newConferenceId = 654;

      // the call has to be subscribed for the http testing controller to get request information
      service.createConference(conference).subscribe(
        data => expect(data).toBe(newConferenceId),
        () => fail("should not send error response")
      );

      const createConferenceRequest = httpTestingController.expectOne("conference");

      expect(createConferenceRequest.request.method).toEqual("POST");

      createConferenceRequest.flush(newConferenceId);
    });

    it("when response return bad request should send message bad request with all errors", () => {
      const errorMessage = JSON.stringify([
        {
          name: "The name is required",
          link: "The link is required",
          price: "The price has to be a number"
        }
      ]);
      const errorResponse = new HttpErrorResponse({
        status: 400
      });

      service.createConference({} as Conference).subscribe(
        _ => fail("should send error response"),
        error => expect(error.error).toEqual(errorMessage)
      )

      const req = httpTestingController.expectOne("conference");
      req.flush(errorMessage, errorResponse);
    })
  });
});
