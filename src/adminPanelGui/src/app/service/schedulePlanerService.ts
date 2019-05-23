import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Schedule } from '../model/schedule';

const httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    })
  };

@Injectable({
    providedIn: 'root',
})


export class SchedulePlannerService {

    private apiEndpointPlannedSchedules = environment.apiEndpointPlannedSchedules;
    private apiEndpointAllSchedules = environment.apiEndpointAllSchedules;
    private apiEndpointCancelSchedule = environment.apiEndpointCancelSchedules;
    private apiEndpointSaveSchedule = environment.apiEndpointSaveSchedules;

    constructor(private http: HttpClient) {

    }

    getScheduleForDay(date: Date): Observable<Schedule[]> {
        let params = new HttpParams()
            .set('date', date.toDateString());
         const url = `${this.apiEndpointPlannedSchedules}`;
        return this.http.get<Schedule[]>(url, { params });
    }


    getAllSchedules(): Observable<Schedule[]> {
        const url = `${this.apiEndpointAllSchedules}`;
         return this.http.get<Schedule[]>(url);
    }

    deleteSchedule(schedule : Schedule) {
        const url = `${this.apiEndpointCancelSchedule}`;
        let param = new HttpParams()
        .set('schedule', JSON.stringify(schedule));
        let options = { params: param };

      //  let body = JSON.stringify(schedule);
        return this.http.delete<Schedule>(url, options);
    }

    saveSchedule(schedule : Schedule) {
        const url = `${this.apiEndpointSaveSchedule}`;
        return this.http.post<Schedule>(url, schedule, httpOptions);
      }
}