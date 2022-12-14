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
  }

  createConference() {
    const conference: Conference = {
      name: this.conferenceForm.controls.name.value,
      price: Number.parseFloat(this.conferenceForm.controls.price.value),
      link: this.conferenceForm.controls.link.value,
      startDate: new Date(this.conferenceForm.controls.startDate.value),
      endDate: new Date(this.conferenceForm.controls.endDate.value)
    }
    this._conferenceService.createConference(conference);
  }
}
