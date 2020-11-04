package main.java.dao;

import main.java.model.BoardingPass;
import main.java.model.Ferry;
import main.java.model.Passenger;
import main.java.model.Schedule;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class Dao {
    public static Path filePath= Paths.get("C:\\Users\\kai\\Desktop\\Java prymid\\ferry-boarding-pass-project\\src\\main\\resources\\tickets.txt");


    SessionFactory sessionFactory = new Configuration().configure("main/resources/hibernate.cfg.xml")
            .addAnnotatedClass(BoardingPass.class)
            .addAnnotatedClass(Passenger.class)
            .addAnnotatedClass(Ferry.class)
            .addAnnotatedClass(Schedule.class)
            .buildSessionFactory();

    //Creates an entity of any type
    public <T> void createEntity(T entity){
        try{
            final Session session =  sessionFactory.openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception ignored){
        }
    }

    //Grabs available times based on origin & destination
    public List getScheduleTimes (String origin, String destination){
        final Session session =  sessionFactory.openSession();
        session.beginTransaction();
        List list = session
                .createQuery("SELECT s.departureTime FROM Schedule s WHERE s.origin =: p1 AND s.destination =: p2")
                .setParameter("p1",origin)
                .setParameter("p2",destination)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        //returns localTimes as strings
        return (List) list.stream().map(time -> time.toString()).collect(Collectors.toList());

    }

    //Grabs available times based on origin & destination
    public Schedule retrieveSchedule (String origin,String destination, String departureTime){
        try {
            final Session session = sessionFactory.openSession();
            session.beginTransaction();
            Schedule schedule = (Schedule) session
                    .createQuery("FROM Schedule s WHERE s.origin =: p1 AND s.destination =: p2 AND s.departureTime = '" + LocalTime.parse(departureTime) + "'")
                    .setParameter("p1", origin)
                    .setParameter("p2", destination)
                    .getSingleResult();
            session.getTransaction().commit();
            session.close();
            return schedule;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    public BoardingPass getTicket(long id){
        try{
            final Session session=sessionFactory.openSession();
            session.beginTransaction();
            BoardingPass bp=session.get(BoardingPass.class,id);
            session.getTransaction().commit();
            return bp;

        }finally{
            System.out.println("read entity error");
        }
    }


    public void printTicket(long id){
        BoardingPass bp=getTicket(id);
        Schedule ss=bp.getSchedule();
        try{
            Files.writeString(filePath,"Ticket Number: "+id+" ", StandardCharsets.UTF_16, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Date: "+ss.getDate().toString()+" ", StandardCharsets.UTF_16, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Departure Time: "+ss.getDepartureTime().toString()+" ",StandardCharsets.UTF_16, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Arrival Time: "+ss.getArrivalTime().toString()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Origin: "+ss.getOrigin().toString()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Price: "+bp.getPrice()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }catch(IOException e){
            System.out.println("IO exception");
        }
    }
}
