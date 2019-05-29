import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { RideCalendarEvent } from 'src/app/model/rideCalendarEvent';
import { Driver } from 'src/app/model/driver';
import { Car } from 'src/app/model/car';
import { Passenger } from 'src/app/model/passenger';
import { CarAvailabilityService } from 'src/app/service/carAvailabilityService';
import { SchedulePlannerService } from 'src/app/service/SchedulePlanerService';
import { VipService } from 'src/app/service/vipService';
import { DriversService } from 'src/app/service/driversService';
import { Schedule } from 'src/app/model/schedule';
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'schedules-table',
  templateUrl: './schedules-table.component.html',
  styleUrls: ['./schedules-table.component.css']
})
export class SchedulesTableComponent implements OnInit {

  @Input() events: Array<RideCalendarEvent>;
  @Output() deleteEventMsg = new EventEmitter<RideCalendarEvent>();
  @Output() saveEventMsg = new EventEmitter<RideCalendarEvent>();
  drivers: Array<String> = new Array();
  driversFullData: Array<Driver> = new Array();

  cars: Array<String> = new Array();
  carsFullData: Array<Car> = new Array()

  VIPs: Array<String> = new Array();
  VIPFullData: Array<Passenger> = new Array();


  constructor(private carService: CarAvailabilityService, private schedulePlannerService: SchedulePlannerService,
    private vipService: VipService, private driversService: DriversService) { }

  ngOnInit() {

    this.driversService.getAllDrivers().subscribe((drivers: Driver[]) => {
      drivers.forEach((driver: Driver) => {
        this.drivers.push(driver.firstName + " " + driver.lastName);
        this.driversFullData.push(driver);
      })
    });

    this.carService.getAllCars().subscribe((cars: Car[]) => {
      cars.forEach((car: Car) => {
        this.cars.push(car.registrationNumber);
        this.carsFullData.push(car);
      })
    });

    this.vipService.getAllVIPs().subscribe((VIPs: Passenger[]) => {
      VIPs.forEach((VIP: Passenger) => {
        this.VIPs.push(VIP.firstName + " " + VIP.lastName);
        this.VIPFullData.push(VIP);
      });
    });

  }
  deleteEvent(eventToDelete: RideCalendarEvent) {
    let scheduleToDelete = eventToDelete.schedule;
    scheduleToDelete.dateFrom = eventToDelete.start;
    scheduleToDelete.dateTo = eventToDelete.end;
    if (scheduleToDelete.id != null) {
      this.schedulePlannerService.deleteSchedule(scheduleToDelete).subscribe(
        () => {
          window.alert("Przejazd został usnięty")
        },
        (httpError : HttpErrorResponse) => {
          window.alert("Błąd serwera: " + httpError.error);
        }
      );
    }

    this.events = this.events.filter(event => event !== eventToDelete);
    this.deleteEventMsg.emit(eventToDelete);
  }

  saveEvent(eventToSave: RideCalendarEvent) {
    let scheduleToSave = eventToSave.schedule;
    if (eventToSave.start > eventToSave.end) {
      window.alert("data zakończenia przejazdu powinna być późniejsza niż data rozpoczęcia");
      return;
    }

    if (scheduleToSave.driver == null || scheduleToSave.car == null || scheduleToSave.passenger == null
      || scheduleToSave.placeFrom == null || scheduleToSave.placeFrom == null) {
      window.alert("proszę uzupełnić wszystkie dane przejazdu");
      return;
    }

    scheduleToSave.dateFrom = eventToSave.start;
    scheduleToSave.dateTo = eventToSave.end;
    eventToSave.title = 'przejazd z ' + scheduleToSave.placeFrom + ' do ' + scheduleToSave.placeTo;

    this.schedulePlannerService.saveSchedule(eventToSave.schedule).subscribe(
      (savedSchedule : Schedule ) => {
        scheduleToSave.id = savedSchedule.id;
        window.alert("Przejazd został zapisany")
      },
      (httpError : HttpErrorResponse) => {
        window.alert(httpError.error);
      }
    );
    this.saveEventMsg.emit(eventToSave);
  }

  changeVIPValue(event, schedule: Schedule) {
    let VIP = event.target.value;
    let firstName = VIP.split(" ")[1];
    let lastName = VIP.split(" ")[2];
    schedule.passenger = this.VIPFullData.find((passenger: Passenger) => passenger.firstName == firstName && passenger.lastName == lastName);
  }

  changeDriverValue(event, schedule: Schedule) {
    let newDriver = event.target.value;
    let firstName = newDriver.split(" ")[1];
    let lastName = newDriver.split(" ")[2];
    schedule.driver = this.driversFullData.find((driver: Driver) => driver.firstName == firstName && driver.lastName == lastName);
  }

  changeCarValue(event, schedule) {
    let carRegistrationNumber = event.target.value.split(" ")[1];
    schedule.car = this.carsFullData.find((car: Car) => car.registrationNumber == carRegistrationNumber);
  }

}
