import { Component, OnInit, Input } from '@angular/core';
import { Car } from 'src/app/model/car';

@Component({
  selector: 'available-cars-list',
  templateUrl: './available-cars-list.component.html',
  styleUrls: ['./available-cars-list.component.css']
})
export class AvailableCarsListComponent implements OnInit {

  @Input()  cars: Car[]
  columnsToDisplay = ['registrationNumber', 'mark', 'model', 'productionDate'];

  constructor() { }

  ngOnInit() {
  }

}
