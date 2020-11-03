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

public class Dao {
    public static Path filePath= Paths.get("C:\\Users\\kai\\Desktop\\Java prymid\\ferry-boarding-pass-project\\src\\main\\resources\\tickets.txt");


    SessionFactory sessionFactory = new Configuration().configure("main/resources/hibernate.cfg.xml")
            .addAnnotatedClass(BoardingPass.class)
            .addAnnotatedClass(Passenger.class)
            .addAnnotatedClass(Ferry.class)
            .addAnnotatedClass(Schedule.class)
            .buildSessionFactory();

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

    public Schedule  getTicket( long id){
        try{
            final Session session=sessionFactory.openSession();
            session.beginTransaction();
            Schedule ss=session.get(Schedule.class,id);
            session.getTransaction().commit();
            return ss;

        }finally{
            System.out.println("read entity error");
        }
    }

    public void printTicket(long id){
        Schedule ss=getTicket(id);
        try{
            Files.writeString(filePath,"Date: "+ss.getDate().toString()+" ", StandardCharsets.UTF_16, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Departure Time: "+ss.getDeparture().toString()+" ",StandardCharsets.UTF_16, StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Arrival Time: "+ss.getArrivalTime().toString()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Origin: "+ss.getOrigin().toString()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
            Files.writeString(filePath,"Original Price: "+ss.getOriginalPrice()+" ",StandardOpenOption.CREATE,StandardOpenOption.APPEND);
        }catch(IOException e){
            System.out.println("IO exception");
        }
    }
}
