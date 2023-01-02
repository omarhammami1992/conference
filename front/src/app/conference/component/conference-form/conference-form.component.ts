import { Component, OnInit } from '@angular/core';
import { ConferenceService } from '../../service/conference.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Conference } from '../../model/conference';

@Component({
  selector: 'app-conference-form',
  templateUrl: './conference-form.component.html',
  styleUrls: ['./conference-form.component.scss']
})
export class ConferenceFormComponent implements OnInit {

  conferenceForm: FormGroup = new FormGroup({});

  constructor(private _conferenceService: ConferenceService, private _formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.conferenceForm = this._formBuilder.group({
      name: '',
      link: '',
      price: '',
      startDate: '',
      endDate: ''
    });
    console.log(this.conferenceForm.value);
  }

  createConference() {
    console.log(this.conferenceForm.value);
    const conference: Conference = {
      name: this.conferenceForm.controls.name.value,
      price: this.conferenceForm.controls.price.value,
      link: this.conferenceForm.controls.link.value,
      startDate: this.conferenceForm.controls.startDate.value,
      endDate: this.conferenceForm.controls.endDate.value
    }
    console.log(conference);
    this._conferenceService.createConference(conference);
  }
}
