package com.pl.pik.restful;

import com.pl.pik.model.Driver;
import com.pl.pik.model.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DriversController {

    @Autowired
    DriverRepository driverRepository;

    @ResponseBody @GetMapping("/api/drivers")
    public ResponseEntity<List<Driver>> getDrivers(){
        return ResponseEntity.ok(driverRepository.findAll());
    }

}
