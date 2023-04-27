import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapConferencePageComponent } from './map-conference-page.component';

describe('MapConferencePageComponent', () => {
  let component: MapConferencePageComponent;
  let fixture: ComponentFixture<MapConferencePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MapConferencePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapConferencePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
