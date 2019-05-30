package com.pl.pik.restful;

import com.pl.pik.model.Passenger;
import com.pl.pik.model.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class PassengersController {
    @Autowired
    PassengerRepository passengerRepository;

    @ResponseBody
    @GetMapping("/api/VIPs")
    public ResponseEntity<List<Passenger>> getPassengers(){
        return ResponseEntity.ok(passengerRepository.findAll());
    }

}
