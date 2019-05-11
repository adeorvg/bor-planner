package com.pl.pik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schedule")
public class Schedule {

    @Id
    int id;

    @Column(name = "driver_id")
    long driverId;

    @Column(name = "passenger_id")
    int passengerId;

    @Column(name = "car_id")
    long carId;

    @Column(name = "place_from")
    String placeFrom;

    @Column(name = "place_to")
    String placeTo;

    @Column(name = "date_from")
    String dateFrom;

    @Column(name = "date_to")
    String dateTo;

    protected Schedule() {
    }

}