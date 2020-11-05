package main.java.model;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private String origin;

    private String destination;

    private LocalTime departureTime;

    private LocalTime arrivalTime;

    private float originalPrice;

    @ManyToOne(fetch=FetchType.LAZY)
    private Ferry ferry;

    public Schedule() {
    }

    public Schedule(String origin, String destination, LocalTime departure, LocalTime estimatedTimeOfArrival, float originalPrice, Ferry ferry) {
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departure;
        this.arrivalTime = estimatedTimeOfArrival;
        this.originalPrice = originalPrice;
        this.ferry = ferry;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", originalPrice=" + originalPrice +
                ", ferry=" + ferry +
                '}';
    }
}
