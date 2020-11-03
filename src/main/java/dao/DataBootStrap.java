package main.java.dao;
import main.java.model.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DataBootStrap {


    static public boolean bootStrapData() {
        try {
            Dao dao = new Dao();
            Passenger passenger = new Passenger("John", "john@email.com", "6786789867", Gender.MALE, 15);
            Passenger passenger2 = new Passenger("Shelby", "shelby@email.com", "7707897865", Gender.FEMALE, 82);
            Passenger passenger3 = new Passenger("Rhonda", "rhonda@email.com", "1231231234", Gender.FEMALE, 11);
            Passenger passenger4 = new Passenger("Ashley", "ashley@email.com", "3454322167", Gender.FEMALE, 35);
            Passenger passenger5 = new Passenger("Matthew", "matthew@email.com", "0987865643", Gender.MALE, 82);
            Passenger passenger6 = new Passenger("Ronald", "ronald@email.com", "2346758907", Gender.MALE, 45);

            ArrayList<Passenger> passengers = new ArrayList<>(Arrays.asList(passenger, passenger2, passenger3));
            ArrayList<Passenger> passengers2 = new ArrayList<>(Arrays.asList(passenger4, passenger5, passenger6));
            //Creating all passengers in the database
            passengers.forEach(dao::createEntity);
            passengers2.forEach(dao::createEntity);

            Ferry ferry = new Ferry("Cruiser001");
            Ferry ferry2 = new Ferry(("JetSwing07"));
            //Adding ferry to database
            dao.createEntity(ferry);
            dao.createEntity(ferry2);

            Schedule schedule = new Schedule(LocalDate.now(), "Sapelo Island", "Little Tybee Island", LocalTime.of(9,30), LocalTime.of(11,32), 35.75f, ferry);
            Schedule schedule1 = new Schedule(LocalDate.now(), "Little Tybee Island", "Sapelo Island", LocalTime.of(13,0), LocalTime.of(15,30), 35.75f, ferry);
            Schedule schedule2 = new Schedule(LocalDate.now(), "St. Catherine's Island ", "Little Tybee Island", LocalTime.of(9,0), LocalTime.of(11,0), 25.75f, ferry2);
            Schedule schedule3 = new Schedule(LocalDate.now(), "Little Tybee Island", "St. Catherine's Island", LocalTime.of(12,0), LocalTime.of(14,0), 25.75f, ferry2);

            ArrayList<Schedule> schedules = new ArrayList<>(Arrays.asList(schedule,schedule1,schedule2,schedule3));
            //Adding schedule to database
            schedules.forEach(dao::createEntity);

            //Adding boarding passes to database
            passengers.forEach(p -> dao.createEntity(new BoardingPass(p, schedule)));
            passengers2.forEach(p -> dao.createEntity(new BoardingPass(p, schedule2)));
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
