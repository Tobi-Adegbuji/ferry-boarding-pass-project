package main.java.model;


import main.java.conditions.Conditions;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BoardingPass {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long boardingPassNum;
        private Date date;
        private String origin;
        private String destination;
        private String arrivalTime;
        private String departureTime;
        private float price;
        @ManyToOne
        private Passenger passenger;
        @ManyToOne
        private Ferry ferry;


        public BoardingPass() {
        }

    public BoardingPass(Date date, String origin, String destination, String arrivalTime, String departureTime, Passenger passenger, Ferry ferry) {
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.passenger = passenger;
        this.ferry = ferry;
        this.price = ferry.getPrice();
        calculatePrice();
    }

    public void calculatePrice(){
        if(Conditions.equalsFemale.and(Conditions.lessThan13).test(this.passenger))
            Conditions.calculatePrice.apply(price,.75f);
        else if(Conditions.equalsFemale.and(Conditions.greaterThan59).test(this.passenger))
            Conditions.calculatePrice.apply(price,.85f);
        else if(Conditions.lessThan13.test(this.passenger))
            Conditions.calculatePrice.apply(price,.50f);
        else if(Conditions.greaterThan59.test(this.passenger))
            Conditions.calculatePrice.apply(price,.60f);
        else if(Conditions.equalsFemale.test(this.passenger))
            Conditions.calculatePrice.apply(price,.25f);
    }

    }


