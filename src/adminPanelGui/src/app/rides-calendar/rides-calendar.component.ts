import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { CalendarView } from 'angular-calendar';
import { isSameMonth,  addDays, subDays} from 'date-fns';
import { RideCalendarEvent } from '../model/rideCalendarEvent';
import { Schedule } from '../model/schedule';
import { SchedulePlannerService } from '../service/SchedulePlanerService';
import * as moment from 'moment';
import { WeekDay } from 'calendar-utils';
import { Polish } from "flatpickr/dist/l10n/pl.js"
import flatpickr from "flatpickr"
import { Subject, BehaviorSubject } from 'rxjs';

flatpickr.localize(Polish);

@Component({
  selector: 'rides-calendar',
  templateUrl: './rides-calendar.component.html',
  styleUrls: ['./rides-calendar.component.css'],
  
})

export class RidesCalendarComponent implements OnInit {
  events: Array<RideCalendarEvent> = new Array();
  dayEvents: Array<RideCalendarEvent> = new Array();

  chosenDateSubject: BehaviorSubject <Date> = new BehaviorSubject<Date>(new Date());

  @ViewChild('modalContent') modalContent: TemplateRef<any>;
  view: CalendarView = CalendarView.Month;
  viewDate: Date = new Date();
  locale: string = 'pl';

  refresh: Subject<any> = new Subject();

  constructor(private schedulePlannerService: SchedulePlannerService) { }

  ngOnInit() {
    this.schedulePlannerService.getAllSchedules().subscribe((schedules: Schedule[]) => {
      schedules.forEach((receivedSchedule: Schedule) => {
        this.events.push({
          start: new Date(receivedSchedule.dateFrom),
          end: new Date(receivedSchedule.dateTo),
          title: 'przejazd z ' + receivedSchedule.placeFrom + ' do ' + receivedSchedule.placeTo,
          allDay: false,
          resizable: {
            beforeStart: true,
            afterEnd: true
          },
          draggable: true,
          schedule: receivedSchedule,
          driver: receivedSchedule.driver.firstName + " " + receivedSchedule.driver.lastName,
          car: receivedSchedule.car.registrationNumber,
          passanger: receivedSchedule.passenger.firstName + " " + receivedSchedule.passenger.lastName
        })
      })

      this.chosenDateSubject.subscribe( date =>  {
        this.dayEvents = this.events.filter(event => moment(date).isSameOrAfter(event.start, 'day') &&
        moment(date).isSameOrBefore(event.end, 'day'));
      })
      this.dayEvents = this.events.filter(event => moment(new Date()).isSameOrAfter(event.start, 'day') &&
        moment(new Date()).isSameOrBefore(event.end, 'day'));

        this.refresh.next();
    });
  
  }

  CalendarView = CalendarView;

  refreshCalendarView(){
    this.refresh.next();
  }

  changeChosenDate(calendarView : CalendarView, change : number){
    if (calendarView === CalendarView.Day){
      if (change > 0) {
        this.chosenDateSubject.next(addDays(this.chosenDateSubject.getValue(), 1))
      } else if (change == 0) {
        this.chosenDateSubject.next(new Date());
      } else if (change < 0) {
        this.chosenDateSubject.next(subDays(this.chosenDateSubject.getValue(), 1))
      }
    }
  }

  dayClicked(date : Date): void {
    if (isSameMonth(date, this.viewDate)) {
      this.viewDate = date;
    }
    this.chosenDateSubject.next(date)
  }

  dayClickedWeekView(weekDay: WeekDay) {
    this.chosenDateSubject.next(weekDay.date);
  }

  addEvent(): void {
    this.events = [
      ...this.events,
      {
        title: '',
        start: this.chosenDateSubject.getValue(),
        end: this.chosenDateSubject.getValue(),
        draggable: true,
        resizable: {
          beforeStart: true,
          afterEnd: true
        },
        schedule: new Schedule(),
        driver: "",
        car: "",
        passanger: ""
      }
    ];
    this.dayEvents = this.events.filter(event => moment(this.chosenDateSubject.getValue()).isSameOrAfter(event.start, 'day') &&
      moment(this.chosenDateSubject.getValue()).isSameOrBefore(event.end, 'day'));
  }

  updateEventListAfterDeletion(eventToDelete : RideCalendarEvent){
     this.events = this.events.filter(event => event !== eventToDelete);
    this.dayEvents = this.dayEvents.filter(event => event !== eventToDelete);
    this.refresh.next();
  }

setView(view: CalendarView) {
    this.view = view;
  }
}
