import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConferenceFormComponent} from './conference-form.component';
import {By} from "@angular/platform-browser";
import {Conference} from "../../model/conference";
import {ConferenceService} from "../../service/conference.service";
import { ReactiveFormsModule } from '@angular/forms';

describe('ConferenceFormComponent', () => {
  let component: ConferenceFormComponent;
  let fixture: ComponentFixture<ConferenceFormComponent>;
  let mockConferenceService = jasmine.createSpyObj(ConferenceService, ["createConference"]);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ConferenceFormComponent],
      providers: [
        {provide: ConferenceService, useValue: mockConferenceService }
      ],
      imports: [ReactiveFormsModule]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConferenceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('template', () => {
    it('should contains conference name input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-name'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contains conference link input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-link'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contains conference price input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-price'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contains conference start date input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-start-date'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contains conference end date input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-end-date'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contains submit button', () => {
      // when
      const conferenceSubmitButton = fixture.debugElement.query(By.css('#conference-submit-button'));

      // then
      expect(conferenceSubmitButton).toBeTruthy();
    });
  });

  describe("methods", () => {
    describe("createConference", () => {
      it("should be called when clicked on submit button", () => {
        // given
        const conferenceSubmitButton = fixture.debugElement.query(By.css('#conference-submit-button'));
        spyOn(component, "createConference");

        // when
        conferenceSubmitButton.nativeElement.click();

        // then
        expect(component.createConference).toHaveBeenCalledTimes(1);
      });

      it("should call conference service to create conference with appropriated data", () => {
        // given
        const conference = {
          name: "conference",
          price: 1000,
          link: "archi hexa",
          startDate: new Date("2022-01-01"),
          endDate: new Date("2022-01-03")
        }
        // spyOn(mockConferenceService, "createConference");
        const conferenceNameInput = fixture.debugElement.query(By.css('#conference-name'));
        conferenceNameInput.nativeElement.value = conference.name;
        conferenceNameInput.nativeElement.dispatchEvent(new Event('input'));

        const conferencePriceInput = fixture.debugElement.query(By.css('#conference-price'));
        conferencePriceInput.nativeElement.value = conference.price;
        conferencePriceInput.nativeElement.dispatchEvent(new Event('input'));

        const conferenceLinkInput = fixture.debugElement.query(By.css('#conference-link'));
        conferenceLinkInput.nativeElement.value = conference.link;
        conferenceLinkInput.nativeElement.dispatchEvent(new Event('input'));

        const conferenceStartDateInput = fixture.debugElement.query(By.css('#conference-start-date'));
        conferenceStartDateInput.nativeElement.value = '2022-01-01';
        conferenceStartDateInput.nativeElement.dispatchEvent(new Event('input'));

        const conferenceEndDateInput = fixture.debugElement.query(By.css('#conference-end-date'));
        conferenceEndDateInput.nativeElement.value = '2022-01-03';
        conferenceEndDateInput.nativeElement.dispatchEvent(new Event('input'));

        fixture.detectChanges();

        // when
        component.createConference();

        // then
        expect(mockConferenceService.createConference).toHaveBeenCalledOnceWith(conference);
      })
    })
  })

});
