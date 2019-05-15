import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AvailableCarsListComponent } from './available-cars-list.component';

describe('AvailableCarsListComponent', () => {
  let component: AvailableCarsListComponent;
  let fixture: ComponentFixture<AvailableCarsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AvailableCarsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AvailableCarsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
