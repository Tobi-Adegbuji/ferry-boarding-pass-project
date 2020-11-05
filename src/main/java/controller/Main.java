package main.java.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.dao.Dao;
import main.java.dao.DataBootStrap;
import main.java.fi_objects.FI;
import main.java.model.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Main extends Application {
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
    private Button button;

    private Animations animations = new Animations();

    private Alert alert;

    private final ArrayList<String> errorsList = new ArrayList<>();
    String error = "";

    private final Dao dao = new Dao();

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    ObservableList<String> locationsList = FXCollections
            .observableArrayList("Sapelo Island", "St. Catherines Island", "Little Tybee Island");


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = new File("src/main/java/controller/home.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Ferry Boarding Pass");
        primaryStage.getIcons().add(new Image("file:logo.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Method runs on startup
    @FXML
    public void initialize() {
        disableDays();
        initChoiceBoxes();
        btnEventHandler();
        //Loading data in database
        DataBootStrap.bootstrapData();

    }

    //Sets up choice boxes
    public void initChoiceBoxes() {
        //Setting items inside choice boxes
        destinationChoiceBox.setItems(locationsList);
        originChoiceBox.setItems(locationsList);
        genderChoiceBox.setItems(FXCollections.observableArrayList("MALE", "FEMALE"));

        //Resets destination to null when choosing a new origin
        originChoiceBox.setOnMouseClicked(event -> {
            destinationChoiceBox.setValue(null);
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
        var timesList = FXCollections.observableList(dao.getScheduleTimes(origin, destination));
        departureTimeChoiceBox.setItems(timesList);
    }


    //TODO: PROVIDE ERROR HANDLING FOR EACH FIELD
    //TODO: RESET ALL FIELDS AND CREATE A POPUP SAYING THANKS FOR BOOKING WITH US
    //Creates Passenger and Booking ID
    public void btnEventHandler() {
        button.setOnAction(event -> {

            if (isValid(name.getText(), phoneNumber.getText(), email.getText(), age.getText(),
                    genderChoiceBox.getValue(), datePicker.getValue(), originChoiceBox.getValue(),
                    destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue())) {
                var gender = genderChoiceBox.getValue().equals("MALE") ? Gender.MALE : Gender.FEMALE;

                var passenger = new Passenger
                        (name.getText(), email.getText(), phoneNumber.getText(), gender, Integer.parseInt(age.getText()));
                dao.createEntity(passenger);

                var schedule = dao.retrieveSchedule(originChoiceBox.getValue(), destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue());

                var boardingPass = new BoardingPass(passenger, schedule, datePicker.getValue());

                dao.createEntity(boardingPass);

                var confirmedTicket = dao.printTicket(passenger, boardingPass, schedule);

                ticketAnchorPane.setVisible(true);
                ticket.setText(confirmedTicket);
            }
            //Creates Single String out of error List
            IntStream.range(0,errorsList.size()).forEach(i -> error += errorsList.get(i) + "\n");

            if (!errorsList.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(error);
                alert.showAndWait();
            }

            errorsList.clear();
            error = "";

        });
    }

    public boolean isValid(String name, String phoneNumber, String email, String age, String gender, LocalDate date, String origin,
                           String destination, String departureTime) {

        if (FI.isEmpty.test(Arrays.asList(name, phoneNumber, email, age)) ||
                FI.isNull.test(Arrays.asList(gender, origin, destination, departureTime)) || date == null) {
            errorsList.add("Please fill in all the boxes.");
            return false;
        }
        //REGEX for name
        var pattern = Pattern.compile("^[a-zA-Z]*$", Pattern.CASE_INSENSITIVE);
        FI.isNotMatching.apply(errorsList,pattern.matcher(name.replaceAll(" ","")),"Invalid Name");
        //REGEX for number
        pattern = Pattern.compile("^\\d{10}$");
        FI.isNotMatching.apply(errorsList,pattern.matcher(phoneNumber),"Invalid Phone Number");
        //REGEX for email
        pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        FI.isNotMatching.apply(errorsList,pattern.matcher(email),"Invalid Email");

        //Exception Handling for age
        try {
            if (Integer.parseInt(age) <= 0 || Integer.parseInt(age) >= 130)
                errorsList.add("Invalid Age");
        } catch (Exception e) {
            errorsList.add("Invalid Age");
        }

        if (errorsList.isEmpty()) {
            formAnchorPane.setVisible(false);
            priceBarAnchorPane.setVisible(false);
            logo.setVisible(false);
            return true;
        }
        else
        return false;
    }

    //Formats date from users
    public LocalDate formatDate(String date) {
        return LocalDate.parse(date, dateFormatter);
    }

    public void disableDays() {
        //Disabling days on datePicker that our prior to current day
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean empty) {
                super.updateItem(localDate, empty);
                LocalDate today = LocalDate.now();
                LocalDate todayPlusYear = today.plusYears(1);
                //using compareTo from the comparable interface to disable dates more than a year
                setDisable(empty || today.compareTo(localDate) > 0 || todayPlusYear.compareTo(localDate) < 0);
            }
        });
    }

}
