package main.java.model;


import main.java.conditions.Conditions;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BoardingPass {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long boardingPassNum;
        private float price;
        @ManyToOne
        private Passenger passenger;
        @ManyToOne
        private Ferry ferry;


        public BoardingPass() {
        }

    public BoardingPass(Passenger passenger, Ferry ferry) {
        this.passenger = passenger;
        this.ferry = ferry;
        price = ferry.getOriginalPrice();
        calculatePrice();
    }

    public void calculatePrice(){
        if(Conditions.equalsFemale.and(Conditions.lessThan13).test(this.passenger))
            price = Conditions.calculatePrice.apply(price,.75f);
        else if(Conditions.equalsFemale.and(Conditions.greaterThan59).test(this.passenger))
            price = Conditions.calculatePrice.apply(price,.85f);
        else if(Conditions.lessThan13.test(this.passenger))
            price = Conditions.calculatePrice.apply(price,.50f);
        else if(Conditions.greaterThan59.test(this.passenger))
            price = Conditions.calculatePrice.apply(price,.60f);
        else if(Conditions.equalsFemale.test(this.passenger))
            price = Conditions.calculatePrice.apply(price,.25f);
    }

    }


