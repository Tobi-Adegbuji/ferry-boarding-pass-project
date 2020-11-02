package main.java.model;


import main.java.conditions.Conditions;

import javax.persistence.*;
import java.util.Date;

@Entity
public class FerryTicket {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long boardingPassNum;
        private Date date;
        private String origin;
        private String destination;
        private String arrivalTime;
        private String departureTime;
        private Double price;
        @ManyToOne
        private Passenger passenger;


        public FerryTicket() {
        }

        public FerryTicket(long boardingPassNum, Date date, String origin, String destination, String arrivalTime, String departureTime, Passenger passenger) {
            this.boardingPassNum = boardingPassNum;
            this.date = date;
            this.origin = origin;
            this.destination = destination;
            this.arrivalTime = arrivalTime;
            this.departureTime = departureTime;
            this.passenger = passenger;
            calculatePrice();
        }


    public void calculatePrice(){
        if(Conditions.equalsFemale.and(Conditions.lessThan13).test(this.passenger))
            this.price = this.price - (this.price *  .75);
        else if(Conditions.equalsFemale.and(Conditions.greaterThan59).test(this.passenger))
            this.price = this.price - (this.price *  .85);
        else if(Conditions.lessThan13.test(this.passenger))
            this.price = this.price - (this.price *  .50);
        else if(Conditions.greaterThan59.test(this.passenger))
            this.price = this.price - (this.price *  .60);
        else if(Conditions.equalsFemale.test(this.passenger))
            this.price = this.price - (this.price *  .25);
    }

    }


