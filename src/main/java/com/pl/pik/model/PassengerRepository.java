package com.pl.pik.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PassengerRepository extends CrudRepository<Passenger, Long> {
    Passenger findPassengerById(int passengerId);

    List<Passenger> findAll();
}