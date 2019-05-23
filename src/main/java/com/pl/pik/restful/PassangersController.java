package com.pl.pik.restful;

import com.pl.pik.model.PassangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PassangersController {
    @Autowired
    PassangerRepository passengerRepository;

    @ResponseBody
    @GetMapping("/api/VIPs")
    public String getAllVips ()  {
        return passengerRepository.findAll().toString();
    }
}
