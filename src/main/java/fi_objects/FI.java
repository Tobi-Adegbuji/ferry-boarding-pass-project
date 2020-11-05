package main.java.fi_objects;
import main.java.model.Gender;
import main.java.model.Passenger;
import org.hibernate.Session;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;

public class FI {

     //Business Logic Variables
     static public Predicate<Passenger> equalsFemale = person -> person.getGender().equals(Gender.FEMALE);
     static public Predicate<Passenger> greaterThan59 = person -> person.getAge() >= 60;
     static public Predicate <Passenger> lessThan13 = person -> person.getAge() <= 12;
     static public BiFunction<Float,Float,Float> discountPrice = (price, discount) -> (float) (price - (price *  discount));



     //Generic variables
     static public Predicate<List<Object>> isNull = list -> list.stream()
             .anyMatch(Objects::isNull);

     static public Predicate<List<String>> isEmpty = list -> list.stream()
             .anyMatch(value -> value.replaceAll(" ","").isEmpty());

     //Adds error message to list if matcher doesnt match
     static public TriConsumer<List<String>, Matcher, String> isNotMatching = (list, matcher, errorMessage) -> {
          if(!matcher.matches())
               list.add(errorMessage);
     };


     //Hibernate Variables
     static public BiFunction<String,Session, Optional<List>> retrieveList = (query, s) -> {
          List list = null;
          try {
               s.beginTransaction();
               list = s.createQuery(query).getResultList();
               s.getTransaction().commit();
               s.close();
               return Optional.ofNullable(list);
          }
          catch (Exception e){
               e.printStackTrace();
          }
          return Optional.ofNullable(list);
     };


}


