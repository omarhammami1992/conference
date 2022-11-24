import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeConferencePageComponent } from './home-conference-page.component';

describe('HomeConferencePageComponent', () => {
  let component: HomeConferencePageComponent;
  let fixture: ComponentFixture<HomeConferencePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeConferencePageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeConferencePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
