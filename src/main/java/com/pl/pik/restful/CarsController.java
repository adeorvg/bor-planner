package com.pl.pik.restful;

import com.pl.pik.model.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarsController {

    @Autowired
    CarRepository carRepository;

    @ResponseBody @GetMapping("/api/cars")
    public String carsController ()  {
        return carRepository.findAll().toString();
    }
}
