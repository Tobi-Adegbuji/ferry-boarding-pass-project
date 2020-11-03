package main.java.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Ferry {
    @Id
    private long id;
    private String origin;
    private String destination;
    private String ferryName;
    private Date departure;
    private Date estimatedTimeOfArrival;
    private float price;

    public Ferry(String origin, String destination, String ferryName, Date departure, Date estimatedTimeOfArrival, float price) {
        this.origin = origin;
        this.destination = destination;
        this.ferryName = ferryName;
        this.departure = departure;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.price = price;
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

    public String getFerryName() {
        return ferryName;
    }

    public void setFerryName(String ferryName) {
        this.ferryName = ferryName;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
