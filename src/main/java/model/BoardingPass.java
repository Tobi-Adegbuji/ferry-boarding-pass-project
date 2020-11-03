package main.java.model;


import main.java.conditions.FI_Objects;

import javax.persistence.*;

@Entity
public class BoardingPass {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long boardingPassNum;
        private float price;
        @ManyToOne
        private Passenger passenger;
        @ManyToOne
        Schedule schedule;

        public BoardingPass() {
        }

    public BoardingPass(Passenger passenger, Schedule schedule) {
        this.passenger = passenger;
        this.schedule = schedule;
        price = schedule.getOriginalPrice();
        calculatePrice();
    }

    public void calculatePrice(){
        if(FI_Objects.equalsFemale.and(FI_Objects.lessThan13).test(this.passenger))
            price = FI_Objects.calculatePrice.apply(price,.75f);
        else if(FI_Objects.equalsFemale.and(FI_Objects.greaterThan59).test(this.passenger))
            price = FI_Objects.calculatePrice.apply(price,.85f);
        else if(FI_Objects.lessThan13.test(this.passenger))
            price = FI_Objects.calculatePrice.apply(price,.50f);
        else if(FI_Objects.greaterThan59.test(this.passenger))
            price = FI_Objects.calculatePrice.apply(price,.60f);
        else if(FI_Objects.equalsFemale.test(this.passenger))
            price = FI_Objects.calculatePrice.apply(price,.25f);
    }

    }


