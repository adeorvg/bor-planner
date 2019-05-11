package com.pl.pik.restful;

import com.pl.pik.model.CarRepository;
import com.pl.pik.model.Schedule;
import com.pl.pik.model.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FreeCarsController {

    @Autowired
    CarRepository freeCars;

    @Autowired
    ScheduleRepository scheduleRepository;

    @GetMapping("/free_cars")
    public String carsController (@RequestParam (value="dateFrom") String dF,
                                  @RequestParam (value="dateTo") String dT)  {
        //
       return freeCars.toString();
    }
}
