package com.pl.pik.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.pik.model.*;
import org.junit.After;
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    private Car carA;
    private Car carB;
    private Driver driverA;
    private Passenger passengerA;


    @Before
    public void setUp() {
        clearRepositories();
        prepareCarsData();
        prepareDriversData();
        preparePassengersData();
    }

    private void clearRepositories() {
        carRepository.deleteAll();
        driverRepository.deleteAll();
        passengerRepository.deleteAll();
        scheduleRepository.deleteAll();
    }

    @Test
    public void getCars() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/cars")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(Stream.of(carA, carB).collect(Collectors.toList())))));
    }

    @Test
    public void getFreeCarsWhenNoneIsBusy() throws Exception {
           mvc.perform(MockMvcRequestBuilders.get("/api/freeCars")
                .param("dateFrom", "2019-05-01 12:00:00")
                .param("dateTo", "2019-05-10 12:00:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(Stream.of(carA, carB).collect(Collectors.toList())))));
    }

    @Test
    public void getFreeCarsWhenOneIsBusy() throws Exception {
        Schedule schedule = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-05-03 13:00:00"), Timestamp.valueOf("2019-05-03 14:00:00"));
        scheduleRepository.save(schedule);

        mvc.perform(MockMvcRequestBuilders.get("/api/freeCars")
                .param("dateFrom", "2019-05-01 12:00:00")
                .param("dateTo", "2019-05-10 12:00:00")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(Stream.of(carB).collect(Collectors.toList())))));

        scheduleRepository.delete(schedule);
    }

    private void prepareCarsData() {
        carA = new Car("XXYYH65", "Audi", "A6", new Date());
        carB = new Car("UHTR43S", "BMW", "x4", new Date());
        carRepository.save(carA);
        carRepository.save(carB);
    }

    private void preparePassengersData() {
        passengerA = new Passenger(123123123, "Barack", "Obama",
                "obama@gmail.com");
        passengerRepository.save(passengerA);
    }

    private void prepareDriversData() {
        driverA = new Driver("123123123", "Adam", "Kowalski",
                "akowalski@gmail.com", "WRW24DD");
        driverRepository.save(driverA);
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown(){
        clearRepositories();
    }


}