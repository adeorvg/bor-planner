import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CalendarView, CalendarDateFormatter } from 'angular-calendar';
import { Subject } from 'rxjs';
import { isSameDay, isSameMonth } from 'date-fns';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomDateFormatter } from './CustomDateFormatter';
import { RideCalendarEvent } from '../model/rideCalendarEvent';
import { Schedule } from '../model/schedule';
import { Car } from '../model/car';
import { Driver } from '../model/driver';
import { DriversService } from '../service/driversService';
import { CarAvailabilityService } from '../service/carAvailabilityService';
import { SchedulePlannerService } from '../service/SchedulePlanerService';
import { Passanger } from '../model/passanger';
import { VipService } from '../service/vipService';

@Component({
  selector: 'rides-calendar',
  templateUrl: './rides-calendar.component.html',
  styleUrls: ['./rides-calendar.component.css'],
  providers: [
    {
      provide: CalendarDateFormatter,
      useClass: CustomDateFormatter
    }
  ]
})

export class RidesCalendarComponent implements OnInit {

  drivers: Array<String> = new Array();
  driversFullData : Array<Driver> = new Array();

  cars: Array<String> = new Array();
  carsFullData: Array<Car> = new Array()

  VIPs : Array<String> = new Array();
  VIPFullData : Array<Passanger> = new Array();

  events: Array<RideCalendarEvent> = new Array();
  dayEvents: Array<RideCalendarEvent> = new Array();

  chosenDate : Date = new Date();

  activeDayIsOpen: boolean

  @ViewChild('modalContent') modalContent: TemplateRef<any>;
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  locale: string = 'pl';


  constructor(private modal: NgbModal, private driversService: DriversService,
    private carService: CarAvailabilityService, private schedulePlannerService : SchedulePlannerService,
    private vipService: VipService) { }

  ngOnInit() {
    this.activeDayIsOpen = true;

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

    this.vipService.getAllVIPs().subscribe((VIPs: Passanger[]) => {
      VIPs.forEach((VIP: Passanger) => {
        this.VIPs.push(VIP.firstName + " " + VIP.lastName);
        this.VIPFullData.push(VIP);
      });
    });

    this.schedulePlannerService.getAllSchedules().subscribe((schedules: Schedule[]) => {
      schedules.forEach((receivedSchedule : Schedule) => {
        this.events.push({
            start: new Date(receivedSchedule.dateFrom),
            end: new Date(receivedSchedule.dateTo),
            title: 'przejazd VIP (jakiś to string)',
            allDay: false,
            resizable: {
              beforeStart: true,
              afterEnd: true
            },
            draggable: true,
            schedule: receivedSchedule,
            driver : receivedSchedule.driver.firstName + " " + receivedSchedule.driver.lastName,
            car: receivedSchedule.car.registrationNumber,
            passanger: receivedSchedule.passenger.firstName + " " + receivedSchedule.passenger.lastName
        })
      })
      this.dayEvents = this.events.filter(event => new Date() >= event.start && new Date() <= event.end);
    });
  }


  CalendarView = CalendarView;

  dayClicked({ date, events }: { date: Date; events: RideCalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      this.viewDate = date;
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
    }
    this.dayEvents = this.events.filter(event => date >= event.start && date <= event.end);
    this.chosenDate = date;
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: 'New event',
        start: this.chosenDate,
        end: this.chosenDate,
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true
        },
        schedule: new Schedule(),
        driver : "",
        car : "",
        passanger: ""
      }
    ];
    this.dayEvents = this.events.filter(event => this.chosenDate >= event.start && this.chosenDate <= event.end);
  }

  deleteEvent(eventToDelete: RideCalendarEvent) {
    let scheduleToDelete = eventToDelete.schedule;
    scheduleToDelete.dateFrom = eventToDelete.start;
    scheduleToDelete.dateTo = eventToDelete.end;
       //TODO result handling - exception alert or dialog confirming that schedule was saved successfully
    this.schedulePlannerService.deleteSchedule(scheduleToDelete).subscribe();

    this.events = this.events.filter(event => event !== eventToDelete);
    this.dayEvents = this.dayEvents.filter(event => event !== eventToDelete);
  }

  saveEvent(eventToSave: RideCalendarEvent) {
    let scheduleToSave = eventToSave.schedule;

    if (eventToSave.start > eventToSave.end){
      window.alert("data zakończenia przejazdu powinna być późniejsza niż data rozpoczęcia");
      return;
    }

    scheduleToSave.dateFrom = eventToSave.start;
    scheduleToSave.dateTo = eventToSave.end;

    console.log(scheduleToSave);
    //TODO result handling - exception alert or dialog confirming that schedule was saved successfully
    this.schedulePlannerService.saveSchedule(eventToSave.schedule).subscribe();
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

  changeVIPValue(event, schedule : Schedule){
      let VIP = event.target.value;
      let firstName = VIP.split(" ")[1];
      let lastName = VIP.split(" ")[2];
      console.log(firstName)
      console.log(lastName)
      console.log(this.VIPFullData)
      schedule.passenger = this.VIPFullData.find((passenger : Passanger) => passenger.firstName == firstName && passenger.lastName == lastName);
  }

  changeDriverValue(event, schedule : Schedule){
    let newDriver = event.target.value;
    let firstName = newDriver.split(" ")[1];
    let lastName = newDriver.split(" ")[2];

    schedule.driver = this.driversFullData.find((driver : Driver) => driver.firstName == firstName && driver.lastName == lastName);
  }

  changeCarValue(event, schedule){
    let carRegistrationNumber = event.target.value.split(" ")[1];
    schedule.car = this.carsFullData.find((car : Car) => car.registrationNumber == carRegistrationNumber);
  }


}
