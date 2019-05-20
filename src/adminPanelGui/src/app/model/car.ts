export class Car {
    registrationNumber : string;
    mark : string;
    model : string;
    productionDate : Date

    constructor(registrationNo : string) {
        this.registrationNumber = registrationNo;
    }
}