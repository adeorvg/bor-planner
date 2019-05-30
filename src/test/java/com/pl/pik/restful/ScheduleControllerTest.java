package com.pl.pik.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.pik.model.*;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private DriverRepository driverRepository;

    private Driver driverA;
    private Driver driverB;
    private Passenger passengerA;
    private Passenger passengerB;
    private Car carA;
    private Car carB;

    @Before
    public void setUp() {
        scheduleRepository.deleteAll();
        prepareDriversData();
        preparePassengersData();
        prepareCarsData();
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
        passengerB = new Passenger(123123124, "Kubuś", "Puchatek",
                "puchatek@gmail.com");
        passengerRepository.save(passengerA);
        passengerRepository.save(passengerB);
    }

    private void prepareDriversData() {
        driverA = new Driver("123123123", "Adam", "Kowalski",
                "akowalski@gmail.com", "WRW24DD");
        driverB = new Driver("123123124", "Zenon", "Wspaniały",
                "zwspanialy@gmail.com", "SS76WD");
        driverRepository.save(driverA);
        driverRepository.save(driverB);
    }

    @Test
    public void getAllSchedulesWhenNoneEmptyResponseBody() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/schedules").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    public void getAllSchedulesWhenNotEmptyScheduleReturned() throws Exception {

        Schedule schedule = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-05-01 13:00:00"), Timestamp.valueOf("2019-05-01 14:00:00"));
        schedule = scheduleRepository.save(schedule);

        mvc.perform(MockMvcRequestBuilders.get("/api/schedules").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[" + asJsonString(schedule) + "]")));

        scheduleRepository.delete(schedule);
    }


    @Test
    public void scheduleSaveWithoutConflicts() throws Exception {
        Schedule schedule = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));
        schedule = scheduleRepository.save(schedule);

        mvc.perform(MockMvcRequestBuilders.post("/api/schedules/save")
                .content(asJsonString(schedule))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void scheduleSaveWithCarConflict() throws Exception {
        Schedule scheduleA = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));
        scheduleRepository.save(scheduleA);

        Schedule scheduleB = new Schedule(driverB, passengerB, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));

        mvc.perform(MockMvcRequestBuilders.post("/api/schedules/save")
                .content(asJsonString(scheduleB))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void scheduleSaveWithDriverConflict() throws Exception {
        Schedule scheduleA = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));
        scheduleRepository.save(scheduleA);

        Schedule scheduleB = new Schedule(driverA, passengerB, carB, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 12:00:00"), Timestamp.valueOf("2019-06-01 13:30:00"));

        mvc.perform(MockMvcRequestBuilders.post("/api/schedules/save")
                .content(asJsonString(scheduleB))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void scheduleSaveWithPassengerConflict() throws Exception {
        Schedule scheduleA = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));
        scheduleRepository.save(scheduleA);

        Schedule scheduleB = new Schedule(driverB, passengerA, carB, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:40:00"), Timestamp.valueOf("2019-06-01 15:30:00"));

        mvc.perform(MockMvcRequestBuilders.post("/api/schedules/save")
                .content(asJsonString(scheduleB))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    public void deleteNonexistentScheduleNotFoundExpected() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/schedules/delete/33")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteScheduleOkExpected() throws Exception {
        Schedule scheduleA = new Schedule(driverA, passengerA, carA, "Gdańsk", "Sopot",
                Timestamp.valueOf("2019-06-01 13:00:00"), Timestamp.valueOf("2019-06-01 14:00:00"));
        scheduleA = scheduleRepository.save(scheduleA);

        mvc.perform(MockMvcRequestBuilders.delete("/api/schedules/delete/" + scheduleA.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertNull(scheduleRepository.findOne(scheduleA.getId()));
    }


    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void tearDown(){
        driverRepository.deleteAll();
        passengerRepository.deleteAll();
        carRepository.deleteAll();
        scheduleRepository.deleteAll();
    }
}