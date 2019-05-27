package com.pl.pik.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="drivers")
public class Driver {

    @Id
    String pesel;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    String email;

    @Column(name = "driving_license_number")
    String drivingLicenseNumber;

    protected Driver() {
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    @Override
    public String toString() {
        return
            "{\"pesel\":\"" + pesel + "\"" +
            ", \"firstName\":\"" + firstName + "\"" +
            ", \"lastName\":\"" + lastName + "\"" +
            ", \"email\":\"" + email + "\"" +
            ", \"drivingLicenseNumber\":\"" + drivingLicenseNumber + "\"" +
            "}";
    }
}