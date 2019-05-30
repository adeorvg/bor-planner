package com.pl.pik.restful;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pl.pik.model.Driver;
import com.pl.pik.model.DriverRepository;
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
public class DriversControllerTest {

    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private MockMvc mvc;

    private List<Driver> drivers = new ArrayList<>();


    @Before
    public void setUp() {
        driverRepository.deleteAll();
        prepareDriversData();
    }

    private void prepareDriversData() {
        Driver driverA = new Driver("123123123", "Adam", "Kowalski",
                "akowalski@gmail.com", "WRW24DD");
        Driver driverB = new Driver("123123124", "Zenon", "Wspaniały",
                "zwspanialy@gmail.com", "SS76WD");

        drivers.add(driverA);
        drivers.add(driverB);

        driverRepository.save(drivers);
    }

    @Test
    public void getDrivers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/drivers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(asJsonString(drivers))));
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