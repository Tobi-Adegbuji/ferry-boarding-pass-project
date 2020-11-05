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
        price = schedule.getOriginalPrice();
        calculatePrice();
    }

    public void calculatePrice() {
        if (FI.equalsFemale.and(FI.lessThan13).test(this.passenger))
            price = FI.discountPrice.apply(price, .75f);
        else if (FI.equalsFemale.and(FI.greaterThan59).test(this.passenger))
            price = FI.discountPrice.apply(price, .85f);
        else if (FI.lessThan13.test(this.passenger))
            price = FI.discountPrice.apply(price, .50f);
        else if (FI.greaterThan59.test(this.passenger))
            price = FI.discountPrice.apply(price, .60f);
        else if (FI.equalsFemale.test(this.passenger))
            price = FI.discountPrice.apply(price, .25f);
    }

}


