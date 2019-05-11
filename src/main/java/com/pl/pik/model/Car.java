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

}
