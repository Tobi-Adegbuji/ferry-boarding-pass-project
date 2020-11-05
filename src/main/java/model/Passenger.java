package main.java.model;

import javax.persistence.*;

@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String email;

    String phoneNumber;

    @Enumerated(EnumType.STRING)
    Gender gender;

    int age;

    public Passenger(){

    }

    public Passenger(Gender gender, int age) {
        this.gender = gender;
        this.age = age;
    }

    public Passenger(String name, String email, String phoneNumber, Gender gender, int age) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
    }




    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }


    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
