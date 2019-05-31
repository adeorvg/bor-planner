package com.pl.pik.restful;

import com.pl.pik.FTP.FtpConnection;
import com.pl.pik.exception.SaveScheduleExceptionCause;
import com.pl.pik.exception.ScheduleOperationException;
import com.pl.pik.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EntityManager entityManager;

    @RequestMapping("")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @RequestMapping("/save")
    public ResponseEntity scheduleSave(@RequestBody Schedule scheduleToSave) {
        try {
            checkCollisionsWithDbSchedules(scheduleToSave);
            scheduleRepository.save(scheduleToSave);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(scheduleToSave.toString());
        } catch (ScheduleOperationException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getExceptionCause());
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity scheduleDelete(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Schedule scheduleToDelete = scheduleRepository.findOne(id);
        if (scheduleToDelete == null) {
            return ResponseEntity.notFound().build();
        }

        scheduleRepository.delete(scheduleToDelete);
        return ResponseEntity.ok().build();

    }

    @RequestMapping("/schedule")
    public String getDriverSchedule(@RequestParam String username, @RequestParam int interval) throws SQLException {
        String databaseInterval = Integer.toString(interval) + " days";
        return GetDriverSchedule(username, databaseInterval);
    }

    private void checkCollisionsWithDbSchedules(Schedule scheduleToSave) throws ScheduleOperationException {
        List<Schedule> busyDrivers = scheduleRepository.findByDriver(scheduleToSave.getDriver());
        if (isDateColiding(scheduleToSave, busyDrivers))
            throw new ScheduleOperationException("Given driver is busy at the time",
                    SaveScheduleExceptionCause.BUSY_DRIVER);

        List<Schedule> busyCars = scheduleRepository.findByCar(scheduleToSave.getCar());
        if (isDateColiding(scheduleToSave, busyCars))
            throw new ScheduleOperationException("Given car is busy at the time",
                    SaveScheduleExceptionCause.BUSY_CAR);

        List<Schedule> busyPassengers = scheduleRepository.findByPassenger(scheduleToSave.getPassenger());
        if (isDateColiding(scheduleToSave, busyPassengers))
            throw new ScheduleOperationException("Given passenger is busy at the time",
                    SaveScheduleExceptionCause.BUSY_PASSENGER);
    }

    private boolean isDateColiding(Schedule scheduleToCompare, List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            if (schedule.getId() == scheduleToCompare.getId())
                continue;
            if (!(schedule.getDateTo().before(scheduleToCompare.getDateFrom()) || schedule.getDateFrom().after(scheduleToCompare.getDateTo())))
                return true;
        }
        return false;
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
        JSONArray jsonArray = new JSONArray();

        for (JSONObject jsonObject : jsonDriverSchedule) {
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }
}
