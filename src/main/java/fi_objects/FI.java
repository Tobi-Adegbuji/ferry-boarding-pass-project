package main.java.fi_objects;
import main.java.model.Gender;
import main.java.model.Passenger;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;

public class FI {
     static public Predicate<Passenger> equalsFemale = person -> person.getGender().equals(Gender.FEMALE);
     static public Predicate<Passenger> greaterThan59 = person -> person.getAge() >= 60;
     static public Predicate <Passenger> lessThan13 = person -> person.getAge() <= 12;
     static public BiFunction<Float,Float,Float> calculatePrice = (price,discount) -> (float) (price - (price *  discount));

     static public Predicate<List<String>> isNull = list -> list.stream()
             .anyMatch(Objects::isNull);
     
     static public Predicate<List<String>> isEmpty = list -> list.stream()
             .anyMatch(value -> value.replaceAll(" ","").isEmpty());

     static public TriConsumer<List<String>, Matcher, String> isNotMatching = (list, matcher, errorMessage) -> {
          if(!matcher.matches())
               list.add(errorMessage);
     };

}


