import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RidesCalendarComponent } from './rides-calendar.component';

describe('RidesCalendarComponent', () => {
  let component: RidesCalendarComponent;
  let fixture: ComponentFixture<RidesCalendarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RidesCalendarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RidesCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
