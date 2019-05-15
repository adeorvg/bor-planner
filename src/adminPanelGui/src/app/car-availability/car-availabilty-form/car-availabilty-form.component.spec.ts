import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CarAvailabiltyFormComponent } from './car-availabilty-form.component';

describe('CarAvailabiltyFormComponent', () => {
  let component: CarAvailabiltyFormComponent;
  let fixture: ComponentFixture<CarAvailabiltyFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CarAvailabiltyFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CarAvailabiltyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
