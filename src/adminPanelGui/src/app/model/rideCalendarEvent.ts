import { CalendarEvent } from 'angular-calendar';
import { Schedule } from './schedule';

export interface RideCalendarEvent extends CalendarEvent {
    id?: string | number;    
    start: Date;
    end?: Date;
    title: string;
    allDay?: boolean;
    resizable?: { beforeStart?: boolean; afterEnd?: boolean; };
    draggable?: boolean;
    meta?: any;
    driver : string;
    car: string;
    passanger: string;

    schedule : Schedule;
}