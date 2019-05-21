package com.pl.pik.restful;

import com.pl.pik.model.Car;
import com.pl.pik.model.CarRepository;
import com.pl.pik.model.Schedule;
import com.pl.pik.model.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class AllCarsController {

    @Autowired
    CarRepository carRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @ResponseBody @GetMapping("/all_cars")
    public String carsController ()  {
        return carRepository.findAll().toString();
    }
}
