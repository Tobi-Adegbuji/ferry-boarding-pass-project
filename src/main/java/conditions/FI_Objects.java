package main.java.conditions;

import main.java.model.Gender;
import main.java.model.Passenger;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public class FI_Objects {

    static public Predicate<Passenger> equalsFemale = person -> person.getGender().equals(Gender.FEMALE);
    static public Predicate<Passenger> greaterThan59 = person -> person.getAge() >= 60;
    static public Predicate <Passenger> lessThan13 = person -> person.getAge() <= 12;
    static public BiFunction<Float,Float,Float> calculatePrice = (price,discount) -> (float) (price - (price *  discount));



}
