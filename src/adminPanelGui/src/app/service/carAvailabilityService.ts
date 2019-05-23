import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Car } from '../model/car';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})

export class CarAvailabilityService {

    private apiEndpointAvailableCars = environment.apiEndpointAvailableCars;
    private apiEndpointAllCars = environment.apiEnddpointAllCars;
    constructor(private http: HttpClient) {

    }

    getCarAvailability(dateFrom: Date, dateTo: Date) : Observable<Car[]>{

        let headers = new Headers();
        

        const params = new HttpParams()
            .set('dateFrom', dateFrom.toISOString().replace('T', ' ').split('.')[0])
            .set('dateTo', dateTo.toISOString().replace('T', ' ').split('.')[0]);

        const url = `${this.apiEndpointAvailableCars}`;
        console.log(url);
      return this.http.get<Car[]>(url, {params});

    }


    getAllCars() : Observable<Car[]> {
        const url = `${this.apiEndpointAllCars}`;
        return this.http.get<Car[]>(url);
    }


}
