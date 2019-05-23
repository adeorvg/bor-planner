package com.pl.pik.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    Car findByRegistrationNumber(String registrationNumber);
    List<Car> findAll();
}
