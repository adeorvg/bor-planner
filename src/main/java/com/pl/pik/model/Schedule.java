package com.pl.pik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name="schedule")
public class Schedule {

    @Id
    long id;

    @Column(name = "driver_id")
    long driverId;

    @Column(name = "passenger_id")
    long passengerId;

    @Column(name = "car_id")
    String carId;

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

    public Schedule(String placeFrom, String placeTo, Timestamp dateFrom, Timestamp dateTo,
                    long driverId, long passengerId, String carId) {
        this.placeFrom = placeFrom; this.placeTo = placeTo;
        this.dateFrom = dateFrom; this.dateTo = dateTo;
        this.driverId = driverId; this.passengerId = passengerId;
        this.carId = carId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return driverId == schedule.driverId &&
                passengerId == schedule.passengerId &&
                carId.equals(schedule.carId) &&
                placeFrom.equals(schedule.placeFrom) &&
                placeTo.equals(schedule.placeTo) &&
                dateFrom.equals(schedule.dateFrom) &&
                dateTo.equals(schedule.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerId, carId, placeFrom, placeTo, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", driverId=" + driverId +
                ", passengerId=" + passengerId +
                ", carId=" + carId +
                ", placeFrom='" + placeFrom + '\'' +
                ", placeTo='" + placeTo + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}