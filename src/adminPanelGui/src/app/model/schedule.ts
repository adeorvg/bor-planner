import { Driver } from "selenium-webdriver/firefox";
import { Car } from "./car";

export class Schedule {
    driver : Driver;
    car : Car;
    startPlace : string;
    endPlace : string;
    dateFrom : Date;
    dateTo : Date;
}