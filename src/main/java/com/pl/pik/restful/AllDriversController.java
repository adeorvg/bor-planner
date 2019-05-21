package com.pl.pik.restful;

import com.pl.pik.model.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AllDriversController {

    @Autowired
    DriverRepository driverRepository;

    @ResponseBody @GetMapping("/all_drivers")
    public String carsController ()  {
        return driverRepository.findAll().toString();
    }
}
