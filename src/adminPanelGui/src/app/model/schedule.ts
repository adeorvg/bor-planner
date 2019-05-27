import { Driver } from "./driver";
import { Car } from "./car";
import { Passenger } from "./passenger";

export class Schedule {
    id : string;
    driver : Driver;
    car : Car;
    passenger : Passenger;
    placeFrom : string;
    placeTo : string;
    dateFrom : Date;
    dateTo : Date;

    constructor(){}
}
