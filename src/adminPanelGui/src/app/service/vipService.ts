import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment'
import { HttpClient, HttpParams } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Driver } from '../model/driver';
import { Passanger } from '../model/passanger';

@Injectable({
    providedIn: 'root',
})

export class VipService {

    private apiEndpointAllVIPs = environment.apiEndpointAllVIPs;

    constructor(private http: HttpClient) {

    }

    getAllVIPs(): Observable<Passanger[]> {
        const url = `${this.apiEndpointAllVIPs}`;
        console.log(url);
        return this.http.get<Passanger[]>(url);

    }


}