import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Driver } from '../model/driver';

@Injectable({
    providedIn: 'root',
})

export class DriversService {

    private apiEndpointAllDrivers = environment.apiEndpointAllDrivers;

    constructor(private http: HttpClient) {

    }

    getAllDrivers(): Observable<Driver[]> {
        const url = `${this.apiEndpointAllDrivers}`;
        console.log(url);
        return this.http.get<Driver[]>(url);

    }


}