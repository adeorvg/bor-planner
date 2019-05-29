package com.pl.pik.restful;

import com.pl.pik.model.Car;
import com.pl.pik.model.CarRepository;
import com.pl.pik.model.Schedule;
import com.pl.pik.model.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class CarsController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @ResponseBody @GetMapping("/api/cars")
    public ResponseEntity<List<Car>> getCars(){
        return ResponseEntity.ok(carRepository.findAll());
    }

    @ResponseBody @GetMapping("/api/freeCars")
    public ResponseEntity<List<Car>> getFreeCars(@RequestParam(value="dateFrom") Timestamp dF,
                                  @RequestParam (value="dateTo") Timestamp dT)  {

        return ResponseEntity.ok(getFreeCarsBetweenTimestamps(dF,dT,carRepository,scheduleRepository));
    }

    List<Car> getFreeCarsBetweenTimestamps(Timestamp dF, Timestamp dT, CarRepository carRepo, ScheduleRepository scheduleRepo){
        List<Car> freeCars = carRepo.findAll();
        List<Schedule> schedulesList = scheduleRepo.findAll();
        for (Schedule schedule:schedulesList) {
            if (! (schedule.getDateFrom().after(dT) || schedule.getDateTo().before(dF)) ) {
                freeCars.remove(schedule.getCar());
            }
        }
        return freeCars;
    }
}
