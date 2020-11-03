package main.java.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    LocalDate date;

    private String origin;

    private String destination;

    private LocalTime departure;

    private LocalTime arrivalTime;

    private float originalPrice;

    @ManyToOne
    private Ferry ferry;

    public Schedule() {
    }

    public Schedule(LocalDate date, String origin, String destination, LocalTime departure, LocalTime estimatedTimeOfArrival, float originalPrice, Ferry ferry) {
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.departure = departure;
        this.arrivalTime = estimatedTimeOfArrival;
        this.originalPrice = originalPrice;
        this.ferry = ferry;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public LocalTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalTime departure) {
        this.departure = departure;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Ferry getFerry() {
        return ferry;
    }

    public void setFerry(Ferry ferry) {
        this.ferry = ferry;
    }
}
