import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateConferencePageComponent } from './create-conference-page.component';
import {By} from "@angular/platform-browser";

describe('CreateConferencePageComponent', () => {
  let component: CreateConferencePageComponent;
  let fixture: ComponentFixture<CreateConferencePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateConferencePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateConferencePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('template', () => {
    it('should contain Conference Form', () => {
      //When
      const appCreateConferenceComponent = fixture
        .debugElement
        .query(By.css('app-conference-form'));

      //Then
      expect(appCreateConferenceComponent).toBeTruthy()
    });





  });


});
