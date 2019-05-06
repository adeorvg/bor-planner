import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  username: string;

  constructor(
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
}
