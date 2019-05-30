package com.pl.pik.model;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "v_driver_schedule")
@NamedStoredProcedureQuery(
        name = "getScheduleForInterval",
        procedureName = "GetScheduleForInterval",
        resultClasses = DriverSchedule.class,
        parameters = {
                @StoredProcedureParameter(name = "p_interval", mode = ParameterMode.IN, type = String.class),
                @StoredProcedureParameter(name = "p_username", mode = ParameterMode.IN, type = String.class),
        }
)
public class DriverSchedule {

    @Id
    @Column(name = "id")
    long id;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "email")
    String email;

    @Column(name = "place_from")
    String placeFrom;

    @Column(name = "place_to")
    String placeTo;

    @Column(name = "date_from")
    Timestamp dateFrom;

    @Column(name = "date_to")
    Timestamp dateTo;

    @Column(name = "passenger_id")
    int passengerId;

    public DriverSchedule() {
    }

    public DriverSchedule(String firstName, String lastName, String email, String placeFrom, String placeTo,
                          Timestamp dateFrom, Timestamp dateTo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.placeFrom = placeFrom;
        this.placeTo = placeTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }


    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void gsetLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
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
        return String.format(
                "Schedule[d=%d, first_name='%s', last_name='%s', email='%s', place_from='%s',place_to='%s', date_from='%s', date_to='%s']",
                id,
                firstName, lastName, email, placeFrom, placeTo,
                dateFrom,
                dateTo
        );
    }
}
