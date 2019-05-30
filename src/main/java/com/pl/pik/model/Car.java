package com.pl.pik.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="cars")
public class Car {

    @Id
    @Column(name="registration_number")
    String registrationNumber;

    String mark;

    String model;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name="production_date")
    Date productionDate;

    public Car() {}

    public Car(String registrationNumber, String mark, String model, Date productionDate) {
        this.registrationNumber = registrationNumber;
        this.mark = mark;
        this.model = model;
        this.productionDate = productionDate;
    }

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

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date date) {
        this.productionDate = date;
    }

    @Override
    public String toString() {
        return
                "{\"registrationNumber\":\"" + registrationNumber + "\"" +
                ",\"mark\":\"" + mark + "\"" +
                ",\"model\":\"" + model + "\"" +
                ",\"productionDate\":\"" + productionDate + "\"" +
                "}";
    }
}
