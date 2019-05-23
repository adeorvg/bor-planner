import { Driver } from "./driver";
import { Car } from "./car";
import { Passanger } from "./passanger";

export class Schedule {
    id : string;
    driver : Driver;
    car : Car;
    passanger : Passanger;
    startPlace : string;
    endPlace : string;
    dateFrom : Date;
    dateTo : Date;

    constructor(){}
}