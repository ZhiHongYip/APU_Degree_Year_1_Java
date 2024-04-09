package com.example.demo;
import java.time.temporal.ChronoUnit;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.BufferUnderflowException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;


public class RoomController implements Initializable {

    @FXML
    private ComboBox<String> roomTypeCombo;
    @FXML
    private DatePicker dateInTBox;
    @FXML
    private DatePicker dateOutTBox;
    @FXML
    private Label label_username;
    @FXML
    private TableView<Room> roomTable;
    @FXML
    private TableColumn<?,?> columnNo;
    @FXML
    private TableColumn<?,?> columnRoomNo;
    @FXML
    private TableColumn<?,?> columnRoomType;
    @FXML
    private TableColumn<?,?> columnCost;
    @FXML
    private TableColumn<?,?> columnMaximumPeople;
    @FXML
    private TableColumn<?,?> columnMaximumExtraBed;
    @FXML
    private TableColumn<?,?> columnAvailability;
    public static int i;
    int maxPeople;
    int maxBed;
    String availability = null;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static long days_difference;
    public static int cost;
    public static int roomNo;
    public static String roomType;
    public static String stringDateIn;
    public static String stringDateOut;
    LocalDate dateIn;
    LocalDate dateOut;
    String chosenType;
    Room r = new Room();

    ObservableList<Room> data = FXCollections.observableArrayList();


    public RoomController(){
    }

    @FXML
    public void goToCustomerList(ActionEvent event) throws IOException {
        Parent customerList = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CustomerList.fxml")));
        Scene scene = new Scene(customerList);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToReserve(ActionEvent event) throws IOException{
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Reserve.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goToRoom(ActionEvent event)throws IOException{
        Parent room = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Room.fxml")));
        Scene scene = new Scene(room);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
@FXML
    public void signOut(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign Out Confirmation");
        alert.setHeaderText((String)null);
        alert.setContentText("Confirm Sign Out?");
        alert.showAndWait().ifPresent((type) ->{
            if (type == ButtonType.CANCEL){
                event.consume();
            } else if (type == ButtonType.OK) {
                try {
                    Parent signOut = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("LoginPage.fxml")));
                    Scene scene = new Scene(signOut);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
                }
        );
    }

    @FXML
    public void search(ActionEvent event) throws IOException{
        LocalDate startDate;
        LocalDate endDate;
        startDate =this.dateInTBox.getValue();
        endDate =this.dateOutTBox.getValue();
        System.out.println(startDate);
        System.out.println(endDate);
        days_difference = DAYS.between(startDate, endDate);
        System.out.println("day difference"+days_difference);
        this.dateIn = startDate;
        this.dateOut = endDate;
        i = 1;
        this.roomTable.getItems().clear();
        this.setCellTable();
        this.dateValidation();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dateInTBox.setValue(LocalDate.now());
        this.dateOutTBox.setValue(LocalDate.now());
        this.comboBox();
        this.setCellTable();
        this.roomTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()>=2){
                    if (RoomController.this.roomTable.getSelectionModel().getSelectedItem().availability.getValue().equals("Available")){
                        cost = RoomController.this.roomTable.getSelectionModel().getSelectedItem().cost.getValue();
                        roomNo = RoomController.this.roomTable.getSelectionModel().getSelectedItem().roomNo.getValue();
                        roomType = RoomController.this.roomTable.getSelectionModel().getSelectedItem().roomType.getValue();
                        stringDateIn = dateInTBox.getValue().format(formatter);
                        stringDateOut = dateOutTBox.getValue().format(formatter);
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PopUpRoom.fxml"));
                        PopUpRoomController popuproomcontroller = Loader.getController();

                        try {
                            Loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Parent p = (Parent)Loader.getRoot();
                        Stage stage2 = new Stage();
                        stage2.setScene(new Scene(p));
                        stage2.setTitle("Extra Information");
                        stage2.show();

                    }else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Room Information");
                        alert.setHeaderText((String)null);
                        alert.setContentText("This room is unavailable");
                        alert.show();
                    }
                }
            }
        });
    }

    private void loadData() {
        String chosenType;
        String line = null;
        String time = null;
        LocalDate checkInDate;
        LocalDate checkOutDate;
        String roomNo;
        chosenType = this.roomTypeCombo.getValue();
        int j = 0;
        int[] unavailableRoomNum = new int[50];
        try {
            if (!chosenType.equals("Any")) {
                BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Room.txt"));
                while ((line = reader.readLine()) != null) {
                    String[] info = line.split(",");
                    if (info[1].equals(chosenType)) {
                        this.data.add(new Room(i, Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), "Available"));
                        ++i;
                    }
                }
                BufferedReader room = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/reserveRoom.txt"));
                while ((time = room.readLine()) != null){
                    String[] reserve = time.split(",");
                    int unavailableRoom = Integer.parseInt(reserve[0]);
                    checkInDate = LocalDate.parse(reserve[2]);
                    checkOutDate = LocalDate.parse(reserve[3]);
                    for(int l=0; l<i-1; ++l) {
                        if((checkOutDate.isBefore(this.dateOut) && checkOutDate.isAfter(this.dateIn) || checkInDate.isEqual(this.dateIn) || checkInDate.isAfter(this.dateIn) && checkInDate.isBefore(this.dateOut)) && unavailableRoom == this.data.get(l).getRoomNo()){
                            System.out.println(this.dateOut);
                            this.data.get(l).setAvailability("Not Available");
                        }
                    }
                }
            }else{
                BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Room.txt"));
                while ((line = reader.readLine()) != null) {
                    String[] info = line.split(",");
                    this.data.add(new Room(i, Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), "Available"));
                    ++i;
                }
                BufferedReader room = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/reserveRoom.txt"));
                while ((time = room.readLine()) != null){
                    String[] reserve = time.split(",");
                    int unavailableRoom = Integer.parseInt(reserve[0]);
                    checkInDate = LocalDate.parse(reserve[2]);
                    checkOutDate = LocalDate.parse(reserve[3]);
                    for(int l=0; l<i-1; ++l) {
                        if((checkOutDate.isBefore(this.dateOut) && checkOutDate.isAfter(this.dateIn) || checkInDate.isEqual(this.dateIn) || checkInDate.isAfter(this.dateIn) && checkInDate.isBefore(this.dateOut)) && unavailableRoom == this.data.get(l).getRoomNo()){
                            System.out.println(this.data.get(l).getRoomNo());
                            this.data.get(l).setAvailability("Not Available");
                        }
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        this.roomTable.setItems(this.data);
    }

    private void setCellTable() {
        this.columnNo.setCellValueFactory(new PropertyValueFactory<>("rowNumber"));
        this.columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        this.columnRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        this.columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        this.columnMaximumPeople.setCellValueFactory(new PropertyValueFactory<>("maximumPeople"));
        this.columnMaximumExtraBed.setCellValueFactory(new PropertyValueFactory<>("maximumExtraBed"));
        this.columnAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    public void comboBox(){
        this.roomTypeCombo.getItems().add("Any");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/roomType.txt"));
            ArrayList<String> roomType = new ArrayList<String>();
            String line = null;
            while((line = reader.readLine()) != null){
                this.roomTypeCombo.getItems().add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        this.roomTypeCombo.setValue("Any");
    }

    public void reset(){
        i = 1;
        this.roomTable.getItems().clear();
        this.dateInTBox.setValue(LocalDate.now());
        this.dateOutTBox.setValue(LocalDate.now());
        this.roomTypeCombo.setValue("Any");
    }

    private void dateValidation(){
        if((this.dateOutTBox.getValue()).isBefore(this.dateInTBox.getValue())){
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Calendar Date Error");
            alert.setHeaderText((String)null);
            alert.setContentText("Check Out date should not be earlier than Check In Date.");
            alert.show();
        } else if((this.dateInTBox.getValue().isBefore(LocalDate.now()))){
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Check In Date Error");
            alert.setHeaderText((String)null);
            alert.setContentText("Check In date should not be Before Present Date");
            alert.show();
        }
        else{
            this.loadData();
        }
    }
}
