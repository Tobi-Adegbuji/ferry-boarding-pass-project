package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.java.dao.Database;
import main.java.dao.DataBootStrap;
import main.java.fi_objects.FI;
import main.java.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class FormController {
    @FXML
    private Label arrivalTime, price, ticket;
    @FXML
    private TextField name, phoneNumber, email, age;
    @FXML
    private ChoiceBox<String> originChoiceBox, destinationChoiceBox, genderChoiceBox, departureTimeChoiceBox;
    @FXML
    private AnchorPane ticketAnchorPane, formAnchorPane, priceBarAnchorPane;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ImageView logo;
    @FXML
    private Button bookBtn, calculateBtn, reRunButton;

    private final Animations animations = new Animations();

    private final ArrayList<String> errorsList = new ArrayList<>();

    private String errorMessage = "";

    private final Database database = new Database();

    ObservableList<String> locationsList = FXCollections
            .observableArrayList("Sapelo Island", "St. Catherines Island", "Little Tybee Island");

    boolean b;

    @FXML
    public void initialize() {
        disableDays();
        initChoiceBoxes();
        initBookBtn();
        initCalculateBtn();
        initReRun();
        //Loading data in database
        DataBootStrap.bootstrapData();
        animations.fadeIn(logo, 4000);
        animations.fadeIn(priceBarAnchorPane, 4000);
        animations.fadeIn(formAnchorPane, 4000);
    }


    //Sets up choice boxes
    public void initChoiceBoxes() {

        destinationChoiceBox.setItems(locationsList);
        originChoiceBox.setItems(locationsList);
        genderChoiceBox.setItems(FXCollections.observableArrayList("MALE", "FEMALE"));

        originChoiceBox.setOnMouseClicked(event -> {
            destinationChoiceBox.setValue(null);
            departureTimeChoiceBox.setDisable(true);
        });

        //Adds times to departure time choice box
        originChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue == null)
                        destinationChoiceBox.setValue(null);
                    else if (!newValue.equals(destinationChoiceBox.getValue()) && destinationChoiceBox.getValue() != null) {
                        departureTimeChoiceBox.setDisable(false);
                        addScheduleTimes(destinationChoiceBox.getValue(), originChoiceBox.getValue());
                    } else {
                        departureTimeChoiceBox.setDisable(true);
                    }
                });

        destinationChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if (newValue != null) {
                        if (!newValue.equals(originChoiceBox.getValue()) && originChoiceBox.getValue() != null) {
                            departureTimeChoiceBox.setDisable(false);
                            addScheduleTimes(originChoiceBox.getValue(), destinationChoiceBox.getValue());
                        } else {
                            departureTimeChoiceBox.setDisable(true);
                        }
                    }
                });
    }


    //Grabs times from database based on destinations and origin
    public void addScheduleTimes(String origin, String destination) {
        var timesList = FXCollections.observableList(database.getScheduleTimes(origin, destination));
        departureTimeChoiceBox.setItems(timesList);
    }

    //Calculates Price with discounts
    public void initCalculateBtn() {
        calculateBtn.setOnAction(event -> {
            if (isValid(name.getText(), phoneNumber.getText(), email.getText(), age.getText(),
                    genderChoiceBox.getValue(), datePicker.getValue(), originChoiceBox.getValue(),
                    destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue())) {

                var gender = genderChoiceBox.getValue().equals("MALE") ? Gender.MALE : Gender.FEMALE;
                var schedule = database.retrieveSchedule(originChoiceBox.getValue(), destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue());
                arrivalTime.setText(schedule.getArrivalTime().toString());
                var passenger = new Passenger(gender, Integer.parseInt(age.getText()));
                price.setText(String.format("%.2f", FI.calculatePrice.apply(schedule.getOriginalPrice(), passenger)));
            }
            hasErrors(errorsList);
        });
    }

    //Books Passenger
    public void initBookBtn() {
        bookBtn.setOnAction(event -> {
            if (isValid(name.getText(), phoneNumber.getText(), email.getText(), age.getText(),
                    genderChoiceBox.getValue(), datePicker.getValue(), originChoiceBox.getValue(),
                    destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue())) {

                var gender = genderChoiceBox.getValue().equals("MALE") ? Gender.MALE : Gender.FEMALE;

                var passenger = new Passenger
                        (name.getText(), email.getText(), phoneNumber.getText(), gender, Integer.parseInt(age.getText()));

                database.createEntity(passenger);

                var schedule = database.retrieveSchedule(originChoiceBox.getValue(), destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue());

                var boardingPass = new BoardingPass(passenger, schedule, datePicker.getValue());

                database.createEntity(boardingPass);

                var confirmedTicket = database.printTicket(passenger, boardingPass, schedule);

                ticketAnchorPane.setVisible(true);
                ticket.setText(confirmedTicket);

                formAnchorPane.setVisible(false);
                priceBarAnchorPane.setVisible(false);
                logo.setVisible(false);
                calculateBtn.setVisible(false);
                reRunButton.setVisible(true);
            }
            hasErrors(errorsList);
        });

    }


    //Pops up alert box if there are errors.
    public boolean hasErrors(List<String> errorsList) {
        IntStream.range(0, errorsList.size()).forEach(i -> errorMessage += errorsList.get(i) + "\n");
        if (!errorsList.isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            errorsList.clear();
            errorMessage = "";
            return true;
        }
        return false;
    }

    //Checks validation of all user fields
    public boolean isValid(String name, String phoneNumber, String email, String age, String gender, LocalDate date, String origin,
                           String destination, String departureTime) {

        if (FI.isEmpty.test(Arrays.asList(name, phoneNumber, email, age)) ||
                FI.isNull.test(Arrays.asList(gender, origin, destination, departureTime, date))) {
            errorsList.add("Please fill in all the boxes.");
            return false;
        }
        //REGEX for name
        var pattern = Pattern.compile("^[a-zA-Z]*$", Pattern.CASE_INSENSITIVE);
        FI.isNotMatching.apply(errorsList, pattern.matcher(name.replaceAll(" ", "")), "Invalid Name");
        //REGEX for number
        pattern = Pattern.compile("^\\d{10}$");
        FI.isNotMatching.apply(errorsList, pattern.matcher(phoneNumber), "Invalid Phone Number");
        //REGEX for email
        pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        FI.isNotMatching.apply(errorsList, pattern.matcher(email), "Invalid Email");


        try {
            if (Integer.parseInt(age) <= 0 || Integer.parseInt(age) > 130)
                errorsList.add("Invalid Age");
        } catch (Exception e) {
            errorsList.add("Invalid Age");
        }

        return errorsList.isEmpty();
    }

    public void disableDays() {
        //Disabling days on datePicker that our prior to current day
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean empty) {
                super.updateItem(localDate, empty);
                LocalDate today = LocalDate.now();
                //using compareTo from the comparable interface to disable dates more than a year
                setDisable(empty || today.compareTo(localDate) > 0);
            }
        });
    }


    public void initReRun() {
        // show everything again
        reRunButton.setOnAction(event -> {

            ticketAnchorPane.setVisible(false);
            ticket.setText("");
            formAnchorPane.setVisible(true);
            priceBarAnchorPane.setVisible(true);
            logo.setVisible(true);
            calculateBtn.setVisible(true);
            reRunButton.setVisible(false);

            //set everything to null or empty
            genderChoiceBox.setValue(null);
            name.setText("");
            email.setText("");
            age.setText("");
            phoneNumber.setText("");
            name.setText("");
            datePicker.setValue(null);
            originChoiceBox.setValue(null);
            destinationChoiceBox.setValue(null);
            departureTimeChoiceBox.setValue(null);
            price.setText("$0.00");
            arrivalTime.setText("");
        });
    }
}
