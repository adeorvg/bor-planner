import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpClientModule} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {HomeService} from "./home.service";
import {Reservation} from "./reservation";
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';

@Component({
  selector: 'home',
  styles: [`
    table {
      width: 100%;
    }
  `],
  templateUrl: './home.component.html',
  providers: [HomeService]
})
export class HomeComponent implements OnInit {

  model: any = {numberOfDays: ''};
  error: any;
  username: string;
  reservations: Reservation[];
  displayedColumns: string[] = ['Od:', 'Do:', 'Początek', 'Koniec', 'Imie', 'Nazwisko','Email', 'Zdjecie'];

  constructor(
    private homeService: HomeService,
    private http: HttpClient,
    private router: Router,
  ) {
  }

  ngOnInit() {
    let url = "http://localhost:8082/user";
    let headers: HttpHeaders = new HttpHeaders({
      "Authorization": "Basic " + sessionStorage.getItem('token')
    });
    let options = {headers: headers}
    this.http.post<Observable<Object>>(url, {}, options)
      .subscribe(res => {
          this.username = res['name']
        },
        error => {
          if (error.status == 401) {
            this.router.navigate(['/login']);
          }
        }
      );
  }

  logout() {
    sessionStorage.setItem('token', '');
  }

  showReservation() : void {
    if ((this.model.numberOfDays =='' || isNaN(this.model.numberOfDays)))
      alert("Podaj prawidlową liczbę dni:");
    else
      this.homeService.getReservations(this.username, this.model.numberOfDays)
        .subscribe(reservations => {
          this.reservations = reservations;
        });
    }
}

