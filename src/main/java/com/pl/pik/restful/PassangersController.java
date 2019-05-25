package com.pl.pik.restful;

import com.pl.pik.model.Passanger;
import com.pl.pik.model.PassangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PassangersController {
    @Autowired
    PassangerRepository passengerRepository;

    @ResponseBody
    @GetMapping("/api/VIPs")
    public ResponseEntity<List<Passanger>> getPassangers(){
        return ResponseEntity.ok(passengerRepository.findAll());
    }

}
