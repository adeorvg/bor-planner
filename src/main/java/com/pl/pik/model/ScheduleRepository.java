package com.pl.pik.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAll();
    List<Schedule> findByDriverId(long driverId);
    List<Schedule> findByPassengerId(long passengerId);
    List<Schedule> findByCarId(String carId);
}
