package main.java.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Date date;
    private String origin;
    private String destination;
    private Date departure;
    private Date estimatedTimeOfArrival;
    private float originalPrice;
    @ManyToOne
    private Ferry ferry;

    public Schedule() {
    }

    public Schedule(Date date, String origin, String destination, Date departure, Date estimatedTimeOfArrival, float originalPrice, Ferry ferry) {
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.originalPrice = originalPrice;
        this.ferry = ferry;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getEstimatedTimeOfArrival() {
        return estimatedTimeOfArrival;
    }

    public void setEstimatedTimeOfArrival(Date estimatedTimeOfArrival) {
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }
}
