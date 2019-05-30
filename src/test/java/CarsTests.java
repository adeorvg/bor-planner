import com.pl.pik.Application;
import com.pl.pik.model.Car;
import com.pl.pik.model.CarRepository;
import com.pl.pik.model.ScheduleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CarsTests{

    @Autowired
    CarRepository carRepo;

    @Autowired
    ScheduleRepository scheduleRepo;

    @Test
    public void givenCarEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        Car car = new Car();
        car.setMark("BMW"); car.setModel("6"); car.setProductionDate(new Date()); car.setRegistrationNumber("123");
        carRepo.save(car);
        Car foundCar = carRepo
                .findByRegistrationNumber("123");

        assertNotNull(foundCar);
        assertEquals(car.getMark(), foundCar.getMark());
    }

}

