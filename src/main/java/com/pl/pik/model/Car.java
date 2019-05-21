package com.pl.pik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cars")
public class Car {
    @Id
    @Column(name="registration_number")
    String registrationNumber;

    String mark;

    String model;

    @Column(name="production_date")
    String productionDate;

    protected Car() {}

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    @Override
    public String toString() {
        return
                "{\"registrationNumber\"=\"" + registrationNumber + "\"" +
                ", \"mark\"=\"" + mark + "\"" +
                ", \"model\"=\"" + model + "\"" +
                ", \"productionDate\"=\"" + productionDate + "\"" +
                "}";
    }
}
