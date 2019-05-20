import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';
import { registerLocaleData } from '@angular/common';
import { CarAvailabilityComponent } from './car-availability/car-availability.component';
import { CarAvailabiltyFormComponent } from './car-availability/car-availabilty-form/car-availabilty-form.component';
import { CarAvailabilityService } from './service/carAvailabilityService';
import { NgbdTimepicker } from './util/timepicker/timerpicker';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule }    from '@angular/common/http';
import { AvailableCarsListComponent } from './car-availability/available-cars-list/available-cars-list.component';
import { MatTableModule } from '@angular/material';
import { RidesCalendarComponent } from './rides-calendar/rides-calendar.component'
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FlatpickrModule } from 'angularx-flatpickr';
import localePl from '@angular/common/locales/pl';
import { SchedulePlannerService } from './service/SchedulePlanerService';

registerLocaleData(localePl);

@NgModule({
  declarations: [
    AppComponent,
    CarAvailabilityComponent,
    CarAvailabiltyFormComponent,
    NgbdTimepicker,
    AvailableCarsListComponent,
    RidesCalendarComponent,
    
   
  ],
  imports: [
    BrowserModule,
    RouterModule,
    FormsModule,
    AngularFontAwesomeModule,
    NgbModule.forRoot(),
    FlatpickrModule.forRoot(),
    NgbModalModule,
    AppRoutingModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatTableModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    })
  ],
  providers: [CarAvailabilityService, SchedulePlannerService],
  bootstrap: [AppComponent]
})
export class AppModule { }
