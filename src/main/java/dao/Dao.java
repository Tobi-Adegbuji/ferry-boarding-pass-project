package main.java.dao;

import main.java.model.BoardingPass;
import main.java.model.Ferry;
import main.java.model.Passenger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Dao {
    SessionFactory sessionFactory = new Configuration().configure("main/resources/hibernate.cfg.xml")
            .addAnnotatedClass(BoardingPass.class)
            .addAnnotatedClass(Passenger.class)
            .addAnnotatedClass(Ferry.class)
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
}
