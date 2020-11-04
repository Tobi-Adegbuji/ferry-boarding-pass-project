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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.dao.Dao;
import main.java.dao.DataBootStrap;
import main.java.model.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

public class Main extends Application {

    //TODO: OPTIONAL: SHOW ETA AND PRICE OF TICKET IN GUI BEFORE BOOKING
    @FXML
    private Label arrivalTime;
    @FXML
    private Label price;
    @FXML
    private TextField name;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private TextField age;
    @FXML
    private ChoiceBox<String> originChoiceBox;
    @FXML
    private ChoiceBox<String> destinationChoiceBox;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private ChoiceBox<String> departureTimeChoiceBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button button;
    @FXML
    private Label ticket;
    @FXML
    private AnchorPane ap;


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
        DataBootStrap.bootStrapData();

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
                    if(newValue == null)
                        destinationChoiceBox.setValue(null);
                     else if (!newValue.equals(destinationChoiceBox.getValue()) && destinationChoiceBox.getValue() != null) {
                        departureTimeChoiceBox.setDisable(false);
                        addScheduleTimes(destinationChoiceBox.getValue(),originChoiceBox.getValue());
                    } else {
                        departureTimeChoiceBox.setDisable(true);
                    }
                });

        destinationChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    if(newValue != null){
                    if (!newValue.equals(originChoiceBox.getValue()) && originChoiceBox.getValue() != null) {
                        departureTimeChoiceBox.setDisable(false);
                        addScheduleTimes(originChoiceBox.getValue(),destinationChoiceBox.getValue());
                    } else {
                        departureTimeChoiceBox.setDisable(true);
                    }
                    }
                });
    }


    //Grabs times from database based on destinations and origin
    public void addScheduleTimes(String origin, String destination) {
        ObservableList<String> timesList = FXCollections.observableList(dao.getScheduleTimes(origin,destination));
        System.out.println(timesList);
        departureTimeChoiceBox.setItems(timesList);
    }


    //TODO: PROVIDE ERROR HANDLING FOR EACH FIELD
    //TODO: RESET ALL FIELDS AND CREATE A POPUP SAYING THANKS FOR BOOKING WITH US
    //Creates Passenger and Booking ID
    public void btnEventHandler() {
        button.setOnAction(event -> {
            Gender gender = genderChoiceBox.getValue().equals("MALE") ? Gender.MALE : Gender.FEMALE;

            Passenger passenger = new Passenger
                    (name.getText(),email.getText(),phoneNumber.getText(),gender,Integer.parseInt(age.getText()));
            dao.createEntity(passenger);

            Schedule schedule = dao.retrieveSchedule(originChoiceBox.getValue(),destinationChoiceBox.getValue(), departureTimeChoiceBox.getValue());

            BoardingPass boardingPass = new BoardingPass(passenger, schedule);

            dao.createEntity(boardingPass);

            String confirmedTicket=dao.printTicket(passenger,boardingPass,schedule);

            //Logging to console
            LOGGER.info(confirmedTicket);

            ap.setVisible(true);
            ticket.setText(confirmedTicket);


        });
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
