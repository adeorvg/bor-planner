package com.pl.pik.model;

import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();

    List<Schedule> findByDriver(Driver driverId);

    List<Schedule> findByPassenger(Passenger passengerId);

    List<Schedule> findByCar(Car carId);

}
