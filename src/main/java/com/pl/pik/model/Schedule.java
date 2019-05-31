package com.pl.pik.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity

@Table(name="schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_generator")
    @SequenceGenerator(name="schedule_generator", sequenceName = "schedule_seq", allocationSize = 1)
    long id;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "place_from")
    String placeFrom;

    @Column(name = "place_to")
    String placeTo;

    @Column(name = "date_from")
    Timestamp dateFrom;

    @Column(name = "date_to")
    Timestamp dateTo;

    protected Schedule() {
    }

    public Schedule(Driver driver, Passenger passenger, Car car, String placeFrom, String placeTo,
                    Timestamp dateFrom, Timestamp dateTo) {
        this.driver = driver;
        this.passenger = passenger;
        this.car = car;
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }

    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Timestamp getDateTo() {
        return dateTo;
    }

    public void setDateTo(Timestamp dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ",\"driver\":" + driver.toString() +
                ",\"passenger\":" + passenger.toString() +
                ",\"car\": "  + car.toString()  +
                ",\"placeFrom\": \"" + placeFrom + "\"" +
                ",\"placeTo\": \"" + placeTo + "\"" +
                ",\"dateFrom\":\"" + dateFrom + "\"" +
                ",\"dateTo\":\"" + dateTo + "\"" +
                "}" ;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}