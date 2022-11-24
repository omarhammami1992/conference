import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  template: `<router-outlet></router-outlet>`,
})
export class ConferenceComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
