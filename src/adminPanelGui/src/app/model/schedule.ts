import { Driver } from "./driver";
import { Car } from "./car";
import { Passanger } from "./passanger";

export class Schedule {
    id : string;
    driver : Driver;
    car : Car;
    passenger : Passanger;
    placeFrom : string;
    placeTo : string;
    dateFrom : Date;
    dateTo : Date;

    constructor(){}
}
