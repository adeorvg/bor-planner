package com.pl.pik.restful;

import com.pl.pik.FTP.FtpConnection;
import com.pl.pik.model.*;
import org.hibernate.procedure.ProcedureCall;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ScheduleController {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PassengerRepository passengerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @RequestMapping("/schedule")
    public String getDriverSchedule(@RequestParam String username, @RequestParam String interval) throws SQLException {
        return GetDriverSchedule(username, interval.replace('-', ' '));
    }


    private String GetDriverSchedule(String username, String interval) throws SQLException {

        StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("getScheduleForInterval");
        storedProcedureQuery.setParameter("p_interval", interval);
        storedProcedureQuery.setParameter("p_username", username);

        List<DriverSchedule> schedules = storedProcedureQuery.getResultList();

        FtpConnection ftpConnection = new FtpConnection();

        List<JSONObject> jsonDriverSchedule = new ArrayList<JSONObject>();

        for (DriverSchedule driverSchedule : schedules) {
            System.out.println(driverSchedule.toString());

            String encodedFilePath = driverSchedule.getFirstName() + "_" + driverSchedule.getLastName() + "_" + driverSchedule.getPassengerId() + ".jpg";
            String encodedFile = ftpConnection.getPassengerPicture(encodedFilePath);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstName", driverSchedule.getFirstName())
                    .put("secondName", driverSchedule.getLastName())
                    .put("placeFrom", driverSchedule.getPlaceFrom())
                    .put("placeTo", driverSchedule.getPlaceTo())
                    .put("email", driverSchedule.getEmail())
                    .put("dateFrom", this.simpleDateFormat.format(new Date(driverSchedule.getDateFrom().getTime())))
                    .put("dateTo", this.simpleDateFormat.format(new Date(driverSchedule.getDateTo().getTime())))
                    .put("picture", encodedFile);
            jsonDriverSchedule.add(jsonObject);
        }

        JSONObject responseJSONObject = new JSONObject();
        int i = 0;
        for (JSONObject jsonObject : jsonDriverSchedule) {
            responseJSONObject.put(Integer.toString(i), jsonObject);
            ++i;
        }
        return responseJSONObject.toString();
    }
}

