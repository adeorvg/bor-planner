package com.pl.pik.hello;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue
    @Column(name="driver_id")
    long driverId;

    private String login;
    private String password;

    protected User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, Login='%s', Password='%s']",
                driverId, login, password);
    }
}
