package main.java.dao;

import main.java.model.BoardingPass;
import main.java.model.Passenger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Dao {
    SessionFactory sessionFactory = new Configuration().configure("main/resources/hibernate.cfg.xml")
            .addAnnotatedClass(BoardingPass.class)
            .addAnnotatedClass(Passenger.class)
            .buildSessionFactory();

    public void createPassenger(Passenger passenger){
        try{
            final Session session =  sessionFactory.openSession();
            session.beginTransaction();
            session.save(passenger);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception ignored){
        }
    }
    public void createFerryTicket(BoardingPass ferryTicket){
        try{
            final Session session =  sessionFactory.openSession();
            session.beginTransaction();
            session.save(ferryTicket);
            session.getTransaction().commit();
            session.close();
        }
        catch(Exception ignored){
        }
    }
}
