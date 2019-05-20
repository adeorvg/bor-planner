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
  cars: Array<String> = new Array();
  events: Array<RideCalendarEvent> = new Array();
  dayEvents: Array<RideCalendarEvent> = new Array();
  VIPs : Array<String> = new Array();
  chosenDate : Date;

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
      drivers.forEach((driver: Driver) => this.drivers.push(driver.lastName));
    });
    this.carService.getAllCars().subscribe((cars: Car[]) => {
      cars.forEach((car: Car) => this.cars.push(car.registrationNumber));
    });
    this.vipService.getAllVIPs().subscribe((VIPs: Passanger[]) => {
      VIPs.forEach((VIP: Passanger) => this.VIPs.push(VIP.lastName));
    });
    this.schedulePlannerService.getAllSchedules().subscribe((schedules: Schedule[]) => {
      schedules.forEach((receivedSchedule : Schedule) => {
        this.events.push({
            start: new Date(receivedSchedule.dateFrom),
            end: new Date(receivedSchedule.dateTo),
            title: 'pzejazd VIP (jakiś to string)',
            allDay: false,
            resizable: {
              beforeStart: true,
              afterEnd: true
            },
            draggable: true,
            schedule: receivedSchedule
        })
      })
      this.dayEvents = this.events.filter(event => isSameDay(event.start, new Date()));
    });
  }


  CalendarView = CalendarView;

  refresh: Subject<any> = new Subject();

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
    this.dayEvents = this.events.filter(event => isSameDay(event.start, date));
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
        schedule: new Schedule()
      }
    ];
    this.dayEvents = this.events.filter(event => isSameDay(event.start, this.chosenDate));
  }

  deleteEvent(eventToDelete: RideCalendarEvent) {
    this.events = this.events.filter(event => event !== eventToDelete);
    this.schedulePlannerService.deleteSchedule(eventToDelete.schedule);
  }

  saveEvent(eventToSave: RideCalendarEvent) {
    this.schedulePlannerService.saveSchedule(eventToSave.schedule);
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }

}
