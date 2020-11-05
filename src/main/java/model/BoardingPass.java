package main.java.model;


import main.java.fi_objects.FI;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BoardingPass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long boardingPassNum;

    private float price;

    private LocalDate date;

    @ManyToOne(fetch=FetchType.LAZY)
    private Passenger passenger;

    @ManyToOne(fetch=FetchType.LAZY)
    Schedule schedule;

    public BoardingPass() {
    }


    public float getPrice() {
        return price;
    }

    public long getBoardingPassNum() {
        return boardingPassNum;
    }

    public LocalDate getDate() {
        return date;
    }


    public BoardingPass(Passenger passenger, Schedule schedule, LocalDate date) {
        this.passenger = passenger;
        this.schedule = schedule;
        this.date = date;
        price = FI.calculatePrice.apply(schedule.getOriginalPrice(),passenger);
    }

}


