package com.pl.pik.model;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    String username;

    @Column(name="driver_id")
    long driverId;

    String password;

    Boolean enabled;

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public String toString() {
        return String.format(
                "User[id=%d, Login='%s', Password='%s']",
                driverId, username, password);
    }
}
