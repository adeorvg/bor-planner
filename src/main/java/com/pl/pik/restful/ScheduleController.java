package com.pl.pik.restful;

import com.pl.pik.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")

public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @RequestMapping("")
    public ResponseEntity<List<Schedule>> getAllSchedules(){
      return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @RequestMapping("/save")
    public boolean scheduleSave(@RequestBody Schedule scheduleToSave){
        if (isCollidindgWithSchedulesInDb(scheduleToSave)){
            return false;
        } else{
            scheduleRepository.save(scheduleToSave);
            return true;
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity scheduleDelete(@PathVariable("id") Long id){
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Schedule scheduleToDelete = scheduleRepository.findOne(id);
        if (scheduleToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        scheduleRepository.delete(scheduleToDelete);
        return ResponseEntity.ok().build();

    }

    private boolean isCollidindgWithSchedulesInDb(Schedule scheduleToSave){
        List<Schedule> busyDrivers= scheduleRepository.findByDriver(scheduleToSave.getDriver());
        isScheduleDateColidingWithSchedules(scheduleToSave,busyDrivers);

        List<Schedule> busyCars = scheduleRepository.findByCar(scheduleToSave.getCar());
        isScheduleDateColidingWithSchedules(scheduleToSave,busyCars);

        List<Schedule> busyPassengers = scheduleRepository.findByPassenger(scheduleToSave.getPassenger());
        isScheduleDateColidingWithSchedules(scheduleToSave,busyPassengers);

        return false;
    }

    private boolean isScheduleDateColidingWithSchedules(Schedule scheduleToCompare, List<Schedule> schedules){
        for (Schedule schedule:schedules){
            if(! (schedule.getDateTo().before(scheduleToCompare.getDateFrom()) || schedule.getDateFrom().after(scheduleToCompare.getDateTo())))
                return true;
        }
        return false;
    }
}
