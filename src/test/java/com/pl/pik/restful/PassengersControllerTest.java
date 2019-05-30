package com.pl.pik.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.pik.model.Passenger;
import com.pl.pik.model.PassengerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PassengersControllerTest {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private MockMvc mvc;

    private List<Passenger> passengers = new ArrayList<>();

    @Before
    public void setUp() {
        passengerRepository.deleteAll();
        Passenger passengerA = new Passenger(123123123, "Barack", "Obama",
                "obama@gmail.com");
        Passenger passengerB = new Passenger(123123124, "Kubuś", "Puchatek",
                "puchatek@gmail.com");

        passengers.add(passengerA);
        passengers.add(passengerB);

        passengerRepository.save(passengers);
    }

    @Test
    public void getPassengers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/VIPs")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(passengers))));
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}