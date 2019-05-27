import { NgModule } from '@angular/core';

import { RouterModule, Routes } from '@angular/router'
import { CarAvailabilityComponent } from './car-availability/car-availability.component';
import { RidesCalendarComponent } from './rides-calendar/rides-calendar.component';


/** routing map */
const routes: Routes = [{
 path: 'calendar',
 component : RidesCalendarComponent     
}, {
  path: 'cars',
  component : CarAvailabilityComponent   
}, {
  path: '',
  pathMatch: 'full',
  redirectTo: 'calendar'
}
];

@NgModule({
    imports: [
      RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
  })
export class AppRoutingModule { }
