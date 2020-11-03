package main.java.dao;

import main.java.model.BoardingPass;
import main.java.model.Ferry;
import main.java.model.Gender;
import main.java.model.Passenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DataBootStrap {


    static public void bootStrapData() {
        Dao dao = new Dao();
        Passenger passenger = new Passenger("John", "john@email.com", "6786789867", Gender.MALE, 15);
        Passenger passenger2 = new Passenger("Shelby", "shelby@email.com", "7707897865", Gender.FEMALE, 82);
        Passenger passenger3 = new Passenger("Rhonda", "rhonda@email.com", "1231231234", Gender.FEMALE, 11);
        Passenger passenger4 = new Passenger("Ashley", "ashley@email.com", "3454322167", Gender.FEMALE, 35);
        Passenger passenger5 = new Passenger("Matthew", "matthew@email.com", "0987865643", Gender.MALE, 82);
        Passenger passenger6 = new Passenger("Ronald", "ronald@email.com", "2346758907", Gender.MALE, 45);


        ArrayList<Passenger> passengers = new ArrayList<>(Arrays.asList(passenger, passenger2, passenger3, passenger4, passenger5, passenger6));

        //Creating all passengers in the database
        passengers.forEach(dao::createEntity);

        Ferry ferry = new Ferry("Savannah", "Treasure Island", "Cruiser001", new Date(), new Date(), new Date(), 45.60f);

        //Adding ferry to database
        dao.createEntity(ferry);

        //Adding boarding passes to database
        passengers.forEach(p -> dao.createEntity(new BoardingPass(p, ferry)));

    }

}
