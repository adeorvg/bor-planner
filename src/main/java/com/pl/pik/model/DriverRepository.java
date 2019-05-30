package com.pl.pik.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    List<Driver> findAll();
}