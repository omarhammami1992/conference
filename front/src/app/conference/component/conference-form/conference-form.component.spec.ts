import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ConferenceFormComponent} from './conference-form.component';
import {By} from "@angular/platform-browser";
import {ConferenceService} from "../../service/conference.service";
import {ReactiveFormsModule} from '@angular/forms';
import SpyObj = jasmine.SpyObj;


describe('ConferenceFormComponent', () => {
  let component: ConferenceFormComponent;
  let fixture: ComponentFixture<ConferenceFormComponent>;
  let mockConferenceService: SpyObj<ConferenceService>;

  beforeEach(async () => {
    mockConferenceService = jasmine.createSpyObj(ConferenceService, ["createConference"]);
    await TestBed.configureTestingModule({
      declarations: [ConferenceFormComponent],
      providers: [
        {provide: ConferenceService, useValue: mockConferenceService}
      ],
      imports: [ReactiveFormsModule]
    })
      .compileComponents();
  });

  afterEach(() => {
    mockConferenceService.createConference.calls.reset();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConferenceFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('template', () => {

    it('should contain conference name label', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-name-label'));

      // then
      expect(conferenceNameInput).toBeTruthy();
      expect(conferenceNameInput.attributes['for']).toEqual('conference-name');
      expect(conferenceNameInput.nativeElement.textContent).toBeTruthy();
    });

    it('should contain conference name input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-name'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contain conference link label', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-link-label'));

      // then
      expect(conferenceNameInput).toBeTruthy();
      expect(conferenceNameInput.attributes['for']).toEqual('conference-link');
      expect(conferenceNameInput.nativeElement.textContent).toBeTruthy();
    });

    it('should contain conference link input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-link'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contain conference price label', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-price-label'));

      // then
      expect(conferenceNameInput).toBeTruthy();
      expect(conferenceNameInput.attributes['for']).toEqual('conference-price');
      expect(conferenceNameInput.nativeElement.textContent).toBeTruthy();
    });

    it('should contain conference price input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-price'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contain conference start-date label', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-start-date-label'));

      // then
      expect(conferenceNameInput).toBeTruthy();
      expect(conferenceNameInput.attributes['for']).toEqual('conference-start-date');
      expect(conferenceNameInput.nativeElement.textContent).toBeTruthy();
    });

    it('should contain conference start date input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-start-date'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it('should contain conference end-date label', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-end-date-label'));

      // then
      expect(conferenceNameInput).toBeTruthy();
      expect(conferenceNameInput.attributes['for']).toEqual('conference-end-date');
      expect(conferenceNameInput.nativeElement.textContent).toBeTruthy();
    });
    it('should contain conference end date input', () => {
      // when
      const conferenceNameInput = fixture.debugElement.query(By.css('#conference-end-date'));

      // then
      expect(conferenceNameInput).toBeTruthy();
    });

    it("should contain conference pricing mode select", () => {
      // when
      const conferencePricingModeInput = fixture.debugElement.query(By.css('#conference-pricing-mode'));

      // then
      expect(conferencePricingModeInput).toBeTruthy();

    });

    describe('pricing modes', () => {
      it("should contain early bird pricing mode component when early bird selected", () => {
        // when
        fillFormInputs({pricingMode: 'earlyBird'});
        fixture.detectChanges()
        // then
        checkPriceModeSelector('app-early-bird-pricing-mode');

      });

      it("should contain group pricing mode component when group selected", () => {
        // when
        fillFormInputs({pricingMode: 'group'});
        fixture.detectChanges()

        // then

        checkPriceModeSelector('app-group-pricing-mode');
      });

      it("should contain attending days pricing mode component when attending days selected", () => {
        // when
        fillFormInputs({pricingMode: 'attendingDays'});
        fixture.detectChanges()

        // then
        checkPriceModeSelector('app-attending-days-pricing-mode');
      });
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
        var spy = spyOn(component, "createConference");

        // when
        conferenceSubmitButton.nativeElement.click();

        // then
        expect(component.createConference).toHaveBeenCalledTimes(1);
        spy.calls.reset();
      });

      it("should call conference service to create conference with appropriated data", () => {
        // given
        const conference = {
          name: "conference",
          price: 1000,
          link: "https://www.archihexa.com/conference",
          startDate: new Date("2022-01-01"),
          endDate: new Date("2022-01-03")
        }
        fillFormInputs({
          name: "conference",
          price: 1000,
          link: "https://www.archihexa.com/conference",
          startDate: "2022-01-01",
          endDate: "2022-01-03",
          pricingMode: 'earlyBird'
        });

        // when
        component.createConference();
        fixture.detectChanges();

        // then
        expect(mockConferenceService.createConference).toHaveBeenCalledOnceWith(conference);
      })

      describe("should not call conference service and display error message when ", () => {

        it("name is not filled", () => {
          // given
          fillFormInputs({
            name: "",
            price: 1000,
            link: "archi hexa",
            startDate: "2022-01-01",
            endDate: "2022-01-03"
          });

          // when
          component.createConference();
          fixture.detectChanges();

          // then
          expect(mockConferenceService.createConference).toHaveBeenCalledTimes(0);
          expect(component.conferenceForm.controls.name.errors).not.toEqual(null);
          const requiredNameErrorMessage = fixture.debugElement.query(By.css('#required-name-error-message'));
          expect(requiredNameErrorMessage).toBeTruthy();
        })

        it("price is equal to 0", () => {
          // given
          fillFormInputs({
            name: "devoxx",
            price: 0,
            link: "archi hexa",
            startDate: "2022-01-01",
            endDate: "2022-01-03"
          });

          // when
          component.createConference();
          fixture.detectChanges();

          // then
          expect(mockConferenceService.createConference).toHaveBeenCalledTimes(0);
          expect(component.conferenceForm.controls.price.errors).not.toEqual(null);
          const requiredPriceErrorMessage = fixture.debugElement.query(By.css('#required-price-error-message'));
          expect(requiredPriceErrorMessage).toBeTruthy();
        })

        it("link is not valid", () => {
          // given
          fillFormInputs({
            name: "devoxx",
            price: 1,
            link: "archi hexa",
            startDate: "2022-01-01",
            endDate: "2022-01-03"
          });

          // when
          component.createConference();
          fixture.detectChanges();

          // then
          expect(mockConferenceService.createConference).toHaveBeenCalledTimes(0);
          expect(component.conferenceForm.controls.link.errors).not.toEqual(null);
          const linkErrorMessage = fixture.debugElement.query(By.css('#required-link-error-message'));
          expect(linkErrorMessage).toBeTruthy();
        })

        it("dates are empty", () => {
          fillFormInputs({
            name: "devoxx",
            price: 1,
            link: "archi hexa",
            startDate: "",
            endDate: ""
          });

          // when
          component.createConference();
          fixture.detectChanges();

          // then
          expect(mockConferenceService.createConference).toHaveBeenCalledTimes(0);
          expect(component.conferenceForm.controls.startDate.errors).not.toEqual(null);
          expect(component.conferenceForm.controls.endDate.errors).not.toEqual(null);
          const dateValuesErrorMessage = fixture.debugElement.query(By.css('#required-valid-dates-error-message'));
          expect(dateValuesErrorMessage).toBeTruthy();
        })

        it("start date after end date", () => {
          fillFormInputs({
            name: "devoxx",
            price: 1,
            link: "archi hexa",
            startDate: "2022-01-10",
            endDate: "2022-01-03"
          });

          // when
          component.createConference();
          fixture.detectChanges();

          // then
          expect(mockConferenceService.createConference).toHaveBeenCalledTimes(0);
          expect(component.conferenceForm.errors).not.toEqual(null);
          const requiredPriceErrorMessage = fixture.debugElement.query(By.css('#required-valid-dates-error-message'));
          expect(requiredPriceErrorMessage).toBeTruthy();
        })
      });
    })
  })

  function fillFormInputs(conference: any) {
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
    conferenceStartDateInput.nativeElement.value = conference.startDate;
    conferenceStartDateInput.nativeElement.dispatchEvent(new Event('input'));

    const conferenceEndDateInput = fixture.debugElement.query(By.css('#conference-end-date'));
    conferenceEndDateInput.nativeElement.value = conference.endDate;
    conferenceEndDateInput.nativeElement.dispatchEvent(new Event('input'));

    const conferencePricingModeInput = fixture.debugElement.query(By.css('#conference-pricing-mode'));
    conferencePricingModeInput.nativeElement.value = conference.pricingMode;
    conferencePricingModeInput.nativeElement.dispatchEvent(new Event('change'));
  }

  function checkPriceModeSelector(selector: string) {

    const earlyBirdPricingMode = fixture.debugElement.query(By.css('app-early-bird-pricing-mode'));
    if (selector === 'app-early-bird-pricing-mode') {
      expect(earlyBirdPricingMode).toBeTruthy();
    } else {
      expect(earlyBirdPricingMode).toBeNull();
    }

    const groupPricingMode = fixture.debugElement.query(By.css('app-group-pricing-mode'));
    if (selector === 'app-group-pricing-mode') {
      expect(groupPricingMode).toBeTruthy();
    } else {
      expect(groupPricingMode).toBeNull();
    }

    const attendingDaysPricingModeMode = fixture.debugElement.query(By.css('app-attending-days-pricing-mode'));
    if (selector === 'app-attending-days-pricing-mode') {
      expect(attendingDaysPricingModeMode).toBeTruthy();
    } else {
      expect(attendingDaysPricingModeMode).toBeNull();
    }
  }
});
