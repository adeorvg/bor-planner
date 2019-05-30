package com.pl.pik.restful;

import com.pl.pik.exception.SaveScheduleExceptionCause;
import com.pl.pik.exception.ScheduleOperationException;
import com.pl.pik.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity scheduleSave(@RequestBody Schedule scheduleToSave){
        try {
            checkCollisionsWithDbSchedules(scheduleToSave);
            scheduleRepository.save(scheduleToSave);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(scheduleToSave.toString());
        } catch(ScheduleOperationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getExceptionCause());
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

    private void checkCollisionsWithDbSchedules(Schedule scheduleToSave) throws ScheduleOperationException {
        List<Schedule> busyDrivers= scheduleRepository.findByDriver(scheduleToSave.getDriver());
        if(isDateColiding(scheduleToSave,busyDrivers))
            throw new ScheduleOperationException("Given driver is busy at the time",
                    SaveScheduleExceptionCause.BUSY_DRIVER);

        List<Schedule> busyCars = scheduleRepository.findByCar(scheduleToSave.getCar());
        if(isDateColiding(scheduleToSave,busyCars))
            throw new ScheduleOperationException("Given car is busy at the time",
                    SaveScheduleExceptionCause.BUSY_CAR);

        List<Schedule> busyPassengers = scheduleRepository.findByPassenger(scheduleToSave.getPassenger());
        if(isDateColiding(scheduleToSave,busyPassengers))
            throw new ScheduleOperationException("Given passenger is busy at the time",
                    SaveScheduleExceptionCause.BUSY_PASSENGER);
    }

    private boolean isDateColiding(Schedule scheduleToCompare, List<Schedule> schedules){
        for (Schedule schedule:schedules){
            if (schedule.getId() == scheduleToCompare.getId())
                continue;
            if(! (schedule.getDateTo().before(scheduleToCompare.getDateFrom()) || schedule.getDateFrom().after(scheduleToCompare.getDateTo())))
                return true;
        }
        return false;
    }
}