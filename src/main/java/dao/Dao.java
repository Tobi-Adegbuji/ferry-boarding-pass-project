package main.java.dao;

import main.java.fi_objects.TriConsumer;
import main.java.model.BoardingPass;
import main.java.model.Ferry;
import main.java.model.Passenger;
import main.java.model.Schedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class Dao {
    public static Path filePath = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\tickets.txt");


    SessionFactory sessionFactory = new Configuration().configure("main/resources/hibernate.cfg.xml")
            .addAnnotatedClass(BoardingPass.class)
            .addAnnotatedClass(Passenger.class)
            .addAnnotatedClass(Ferry.class)
            .addAnnotatedClass(Schedule.class)
            .buildSessionFactory();


    //Creates an entity of any type
    public <T> void createEntity(T entity) {
        try {
            final var session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
        } catch (Exception ignored) {
        }
    }

    public Function<String, List> retrieveItem = query -> {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = (List) session.createQuery(query).getResultList();
        session.getTransaction().commit();
        session.close();
        return list;
    };

    //Grabs available times based on origin & destination
    public List<String> getScheduleTimes(String origin, String destination) {
        var list = retrieveItem.apply("SELECT s.departureTime FROM Schedule s WHERE s.origin = '" + origin + "' AND s.destination = '" + destination + "'");
        return (List<String>) list.stream().map(Object::toString).collect(Collectors.toList());
    }

    //Grabs available times based on origin & destination
    public Schedule retrieveSchedule(String origin, String destination, String departureTime) {
        var list = retrieveItem.apply("FROM Schedule s WHERE s.origin = '" + origin + "' AND s.destination = '" + destination + "' AND s.departureTime = '" + LocalTime.parse(departureTime) + "'");
        return (Schedule) list.get(0);
    }

    public String printTicket(Passenger p, BoardingPass bp, Schedule ss) {

        var ticketDetails = "Passenger Name: " + p.getName() + "\n" +
                "Phone Number: " + p.getPhoneNumber() + "\n" +
                "Email: " + p.getEmail() + "\n" +
                "Gender: " + p.getGender() + "\n" +
                "Age: " + p.getAge() + "\n" +
                "Ticket Number: " + bp.getBoardingPassNum() + "\n" +
                "Date: " + bp.getDate().toString() + "\n" +
                "Departure Time: " + ss.getDepartureTime().toString() + "\n" +
                "Arrival Time: " + ss.getArrivalTime().toString() + "\n" +
                "Origin: " + ss.getOrigin().toString() + "\n" +
                "Destination: " + ss.getDestination().toString() + "\n" +
                "Price: " + "$" + bp.getPrice();

        try {
            Files.writeString(filePath, ticketDetails);
        } catch (IOException e) {
            LOGGER.error("Error writing out");
        }
        return ticketDetails;
    }
}
