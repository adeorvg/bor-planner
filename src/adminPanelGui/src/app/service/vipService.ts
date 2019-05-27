import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Driver } from '../model/driver';
import { Passenger } from '../model/passenger';

@Injectable({
    providedIn: 'root',
})

export class VipService {

    private apiEndpointAllVIPs = environment.apiEndpointAllVIPs;

    constructor(private http: HttpClient) {

    }

    getAllVIPs(): Observable<Passenger[]> {
        const url = `${this.apiEndpointAllVIPs}`;
        console.log(url);
        return this.http.get<Passenger[]>(url);

    }


}
