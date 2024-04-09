package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import java.awt.MouseInfo;
//import java.awt.Point;
//import java.awt.PointerInfo;
//import java.awt.event.MouseEvent;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerListController implements Initializable {
    @FXML
    private TextField nameTBox;
    @FXML
    private TextField phoneNumberTBox;
    @FXML
    private TextField roomNumberTBox;

    @FXML
    private CheckBox checkOutList;
    @FXML
    private CheckBox bookedList;
    @FXML
    private CheckBox checkInList;
    @FXML
    private RadioButton selectAll;
    @FXML
    private TableView<Customer> customerList;

    @FXML
    private TableColumn<?, ?> columActualCheckInDate;

    @FXML
    private TableColumn<?, ?> columActualCheckOutDate;

    @FXML
    private TableColumn<?, ?> columnCheckInDate;

    @FXML
    private TableColumn<?, ?> columnCheckOutDate;

    @FXML
    private TableColumn<?, ?> columnCustomerName;

    @FXML
    private TableColumn<?, ?> columnCustomerNo;

    @FXML
    private TableColumn<?, ?> columnPhoneNumber;

    @FXML
    private TableColumn<?, ?> columnReservedTime;

    @FXML
    private TableColumn<?, ?> columnRoomNo;

    @FXML
    private TableColumn<?, ?> columnStatus;

    public static int customerID;
    public static int roomNO;
    public static String dateIN;
    public static String dateOUT;

    public int i = 1;
    public int totalAmount = 0;
    public int extraBed;
    public int totalPeople;
    public int totalDays;
    public int days_difference;
    public String[] date_in;
    public String[] date_out;
    LocalDateTime startDate;
    LocalDateTime endDate;
    LocalDate formattedDateInTemp;
    String formattedDateIn;
    public static SimpleStringProperty dummyPhoneNumber1;
    String name;
    String phoneNumber;
    String roomNo;
    String dateIn;
    String dateOut;
    String status;
    String check;
    Customer selectedCustomer;
    public SimpleStringProperty temp = new SimpleStringProperty();
    ContextMenu contextMenu1 = new ContextMenu();
    ContextMenu contextMenu2 = new ContextMenu();
    ContextMenu contextMenu3 = new ContextMenu();
    MenuItem checkIn = new MenuItem("Check In");
    MenuItem checkOut = new MenuItem("Check Out");
    MenuItem cancel = new MenuItem("Cancel");
    MenuItem receipt = new MenuItem("Receipt");

    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public DateTimeFormatter formatterTemp = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @FXML
    ObservableList<Customer> CustomerData;

    public CustomerListController(){
    }

    public String getTemp() {
        return temp.get();
    }

    public SimpleStringProperty tempProperty() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp.set(temp);
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
    public void signOut(ActionEvent event)throws IOException{
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
    void select_All(ActionEvent event){
        if (this.selectAll.isSelected()){
            this.bookedList.setSelected(true);
            this.checkOutList.setSelected(true);
            this.checkInList.setSelected(true);
        }else {
            this.bookedList.setSelected(false);
            this.checkOutList.setSelected(false);
            this.checkInList.setSelected(false);
        }
    }

    @FXML
    void search(ActionEvent event) throws IOException {
        this.i = 1;
        this.customerList.getItems().clear();
        this.setCellTable();
        this.CustomerData = FXCollections.observableArrayList();
        this.loadData();
    }

    private void setCellTable(){
        this.columnCustomerNo.setCellValueFactory(new PropertyValueFactory<>("customerNo"));
        this.columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        this.columnReservedTime.setCellValueFactory(new PropertyValueFactory<>("reservedTime"));
        this.columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.columnPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber1"));
        this.columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
        this.columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("dateOut"));
        this.columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        this.columActualCheckInDate.setCellValueFactory(new PropertyValueFactory<>("actualDateIn"));
        this.columActualCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("actualDateOut"));
    }

    private void loadData()throws IOException{
        String line = null;
        String line1 = null;
        String line2 = null;
        String [] sRName;
        String [] sRPhone;
        String [] sRRoom;
        String customerID = null;
        String customerName = null;
        String customerPhoneNumber = null;
        String roomNo = null;
        this.name = this.nameTBox.getText();
        this.roomNo = this.roomNumberTBox.getText();
        this.phoneNumber = this.phoneNumberTBox.getText();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        BufferedReader reader2 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Room.txt"));
        if (!this.nameTBox.getText().trim().isEmpty() && this.phoneNumberTBox.getText().trim().isEmpty() && this.roomNumberTBox.getText().trim().isEmpty()){
            System.out.println(this.nameTBox.getText().trim());
            System.out.println("search");
            System.out.println(this.name);
            while ((line = reader.readLine()) != null){
                String[] info = line.split(",");
                System.out.println(Arrays.toString(info));
                if (info[2].contains(this.name)){
                    System.out.println(info[2]);
                    customerID = info[0];
                    BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                    while ((line1 = reader1.readLine()) != null) {
                        String[] customer = line1.split(",");
                        if (customer[2].equals(customerID)) {
                            customerName = info[2];
                            if(this.checkOutList.isSelected()){
                                this.addCheckOutList(customer,customerName);
                            }
                            if (this.bookedList.isSelected()){
                                this.addBookedList(customer, customerName);
                            }
                            if (this.checkInList.isSelected()){
                                this.addCheckInList(customer, customerName);
                            }
                        }
                    }

                }
            }
        } else if (this.nameTBox.getText().trim().isEmpty() && !this.phoneNumberTBox.getText().trim().isEmpty() && this.roomNumberTBox.getText().trim().isEmpty()) {
            System.out.println("sort by phone number");
            while ((line = reader.readLine()) != null){
                String[] info = line.split(",");
                System.out.println(Arrays.toString(info));
                if (info[3].contains(this.phoneNumber)){
                    System.out.println(info[3]);
                    customerPhoneNumber = info[3];
                    BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                    while ((line1 = reader1.readLine()) != null) {
                        String[] customer = line1.split(",");
                        System.out.println(Arrays.toString(customer));
                        if (customer[3].equals(customerPhoneNumber)) {
                            customerName = info[2];
                            if(this.checkOutList.isSelected()){
                                this.addCheckOutList(customer,customerName);
                            }
                            if (this.bookedList.isSelected()){
                                this.addBookedList(customer, customerName);
                            }
                            if (this.checkInList.isSelected()){
                                this.addCheckInList(customer, customerName);
                            }
                        }
                    }

                }
            }
        } else if (this.nameTBox.getText().trim().isEmpty() && this.phoneNumberTBox.getText().trim().isEmpty() && !this.roomNumberTBox.getText().trim().isEmpty()) {
            System.out.println("sort by roomNo");
            while ((line2 = reader2.readLine()) != null){
                String[] info = line2.split(",");
                System.out.println(Arrays.toString(info));
                if (info[0].contains(this.roomNo)) {
                    System.out.println(info[0]);
                    roomNo = info[0];
                    BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                    while ((line1 = reader1.readLine()) != null) {
                        String[] customer = line1.split(",");
                        System.out.println(Arrays.toString(customer));
                        if (customer[0].equals(roomNo)) {
                            System.out.println("get"+customer[0]);
                            BufferedReader reader4 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
                            while ((line = reader4.readLine()) != null) {
                                String[] info1 = line.split(",");
                                System.out.println(Arrays.toString(info1));
                                if (customer[2].equals(info1[0])) {
                                    System.out.println(info1[2]);
                                    customerName = info1[2];
                                    if(this.checkOutList.isSelected()){
                                        this.addCheckOutList(customer,customerName);
                                    }
                                    if (this.bookedList.isSelected()){
                                        this.addBookedList(customer, customerName);
                                    }
                                    if (this.checkInList.isSelected()){
                                        this.addCheckInList(customer, customerName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (!this.nameTBox.getText().trim().isEmpty() && !this.phoneNumberTBox.getText().trim().isEmpty() && this.roomNumberTBox.getText().trim().isEmpty()) {
            System.out.println("Sort by name and phoneNumber");
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                System.out.println(Arrays.toString(info));
                if (info[2].contains(this.name) && info[3].contains(this.phoneNumber)) {
                    System.out.println(info[2]);
                    customerID = info[0];
                    customerPhoneNumber = info[3];
                    BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                    while ((line1 = reader1.readLine()) != null) {
                        String[] customer = line1.split(",");
                        if (customer[2].equals(customerID) && customer[3].equals(customerPhoneNumber)) {
                            customerName = info[2];
                            if(this.checkOutList.isSelected()){
                                this.addCheckOutList(customer,customerName);
                            }
                            if (this.bookedList.isSelected()){
                                this.addBookedList(customer, customerName);
                            }
                            if (this.checkInList.isSelected()){
                                this.addCheckInList(customer, customerName);
                            }
                        }
                    }
                }
            }
        } else if (!this.nameTBox.getText().trim().isEmpty() && this.phoneNumberTBox.getText().trim().isEmpty() && !this.roomNumberTBox.getText().trim().isEmpty()) {
            System.out.println("sort by name and room");
            //room.txt
            while ((line2 = reader2.readLine()) != null){
                System.out.println("stage 1");
                String[] info = line2.split(",");
                System.out.println(Arrays.toString(info));
                BufferedReader reader4 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
                while ((line = reader4.readLine())!= null){
                    String[] info2 = line.split(",");
                    System.out.println(Arrays.toString(info2));
                    if (info[0].contains(this.roomNo) && info2[2].contains(this.name)) {
                        System.out.println("Stage 2");
                        roomNo = info[0];
                        System.out.println("roomNo" + roomNo);
                        customerID = info2[0];
                        System.out.println("customerID" + customerID);
                        BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                        while ((line1 = reader1.readLine()) != null) {
                            String[] customer = line1.split(",");
                            System.out.println(Arrays.toString(customer));
                            if (customer[0].equals(roomNo) && customer[2].equals(customerID)) {
                                System.out.println("Stage 3");
                                System.out.println("get"+customer[0]);
                                customerName = info2[2];
                                if(this.checkOutList.isSelected()){
                                    this.addCheckOutList(customer,customerName);
                                }
                                if (this.bookedList.isSelected()){
                                    this.addBookedList(customer, customerName);
                                }
                                if (this.checkInList.isSelected()){
                                    this.addCheckInList(customer, customerName);
                                }
                            }
                        }
                    }
                }
            }

        }else if (this.nameTBox.getText().trim().isEmpty() && !this.phoneNumberTBox.getText().trim().isEmpty() && !this.roomNumberTBox.getText().trim().isEmpty()){
            System.out.println("sort by phoneNumber and Room");
            while ((line2 = reader2.readLine()) != null){
                System.out.println("stage 1");
                String[] info = line2.split(",");
                System.out.println(Arrays.toString(info));
                BufferedReader reader4 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
                while ((line = reader4.readLine())!= null){
                    String[] info2 = line.split(",");
                    System.out.println(Arrays.toString(info2));
                    if (info[0].contains(this.roomNo) && info2[3].contains(this.phoneNumber)) {
                        System.out.println("Stage 2");
                        roomNo = info[0];
                        System.out.println("roomNo" + roomNo);
                        customerPhoneNumber = info2[3];
                        System.out.println("PhoneNumber" + customerPhoneNumber);
                        BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                        while ((line1 = reader1.readLine()) != null) {
                            String[] customer = line1.split(",");
                            System.out.println(Arrays.toString(customer));
                            if (customer[0].equals(roomNo) && customer[3].equals(customerPhoneNumber)) {
                                System.out.println("Stage 3");
                                System.out.println("get"+customer[0]);
                                customerName = info2[2];
                                if(this.checkOutList.isSelected()){
                                    this.addCheckOutList(customer,customerName);
                                }
                                if (this.bookedList.isSelected()){
                                    this.addBookedList(customer, customerName);
                                }
                                if (this.checkInList.isSelected()){
                                    this.addCheckInList(customer, customerName);
                                }
                            }
                        }
                    }
                }
            }

        }else if (!this.nameTBox.getText().trim().isEmpty() && !this.phoneNumberTBox.getText().trim().isEmpty() && !this.roomNumberTBox.getText().trim().isEmpty()){
            System.out.println("sort by all");
            while ((line2 = reader2.readLine()) != null){
                System.out.println("stage 1");
                String[] info = line2.split(",");
                System.out.println(Arrays.toString(info));
                BufferedReader reader4 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
                while ((line = reader4.readLine())!= null){
                    String[] info2 = line.split(",");
                    System.out.println(Arrays.toString(info2));
                    if (info[0].contains(this.roomNo) && info2[3].contains(this.phoneNumber) && info2[2].contains(this.name)) {
                        System.out.println("Stage 2");
                        roomNo = info[0];
                        System.out.println("roomNo" + roomNo);
                        customerPhoneNumber = info2[3];
                        System.out.println("PhoneNumber" + customerPhoneNumber);
                        customerID = info2[0];
                        System.out.println("CustomerId: "+ customerID);
                        BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                        while ((line1 = reader1.readLine()) != null) {
                            String[] customer = line1.split(",");
                            System.out.println(Arrays.toString(customer));
                            if (customer[0].equals(roomNo) && customer[3].equals(customerPhoneNumber) && customer[2].equals(customerID)) {
                                System.out.println("Stage 3");
                                System.out.println("get"+customer[0]);
                                customerName = info2[2];
                                if(this.checkOutList.isSelected()){
                                    this.addCheckOutList(customer,customerName);
                                }
                                if (this.bookedList.isSelected()){
                                    this.addBookedList(customer, customerName);
                                }
                                if (this.checkInList.isSelected()){
                                    this.addCheckInList(customer, customerName);
                                }
                            }
                        }
                    }
                }
            }

        } else {
            while ((line = reader.readLine()) != null) {
                String[] info = line.split(",");
                System.out.println(Arrays.toString(info));
                customerID = info[0];
                BufferedReader reader1 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                while ((line1 = reader1.readLine()) != null) {
                    String[] customer = line1.split(",");
                    System.out.println(Arrays.toString(customer));
                    if (customer[2].equals(customerID)) {
                        System.out.println("Linking");
                        customerName = info[2];
                        if (this.checkOutList.isSelected()) {
                            this.addCheckOutList(customer, customerName);
                        }
                        if (this.bookedList.isSelected()) {
                            this.addBookedList(customer, customerName);
                        }
                        if (this.checkInList.isSelected()) {
                            this.addCheckInList(customer, customerName);
                        }
                    }
                }
            }

        }
        System.out.println(CustomerData);
        this.customerList.setItems(this.CustomerData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showCustomerInfo();
        this.reset();
        this.contextMenu1.getItems().addAll(this.checkOut);
        this.contextMenu1.setAutoHide(true);
        this.contextMenu2.getItems().addAll(this.checkIn, this.cancel);
        this.contextMenu2.setAutoHide(true);
        this.contextMenu3.getItems().addAll(this.receipt);
        this.contextMenu3.setAutoHide(true);
        this.CustomerMenuItem();
        this.bookedList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (CustomerListController.this.bookedList.isSelected()){
                    if (CustomerListController.this.checkInList.isSelected() && CustomerListController.this.checkOutList.isSelected()){
                        CustomerListController.this.selectAll.setSelected(true);
                    }
                }else {
                    CustomerListController.this.selectAll.setSelected(false);
                }
            }
        });
        this.checkOutList.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (CustomerListController.this.checkOutList.isSelected()) {
                    if (CustomerListController.this.bookedList.isSelected() && CustomerListController.this.checkInList.isSelected()) {
                        CustomerListController.this.selectAll.setSelected(true);
                    }
                } else {
                    CustomerListController.this.selectAll.setSelected(false);
                }

            }
        });
        this.checkInList.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (CustomerListController.this.checkInList.isSelected()) {
                    if (CustomerListController.this.checkOutList.isSelected() && CustomerListController.this.bookedList.isSelected()) {
                        CustomerListController.this.selectAll.setSelected(true);
                    }
                } else {
                    CustomerListController.this.selectAll.setSelected(false);
                }

            }
        });

    }

    private void addCheckOutList(String[]info, String customerName)throws IOException{
        if (info[8].equals("Checked Out")){
            this.CustomerData.add(new Customer(this.i, Integer.parseInt(info[0]), info[1], customerName,info[3],info[4],info[5],info[6],info[7],info[8]));
            this.i++;
        }
    }

    private void addBookedList(String[]info, String customerName)throws IOException{
        if (info[8].equals("Booked")){
            System.out.println(info[4]);
            this.CustomerData.add(new Customer(this.i, Integer.parseInt(info[0]), info[1], customerName,info[3], info[4],info[5],info[6],info[7],info[8]));
            System.out.println(CustomerData.get(0).dateIn);
            this.i++;
        }
    }

    private void addCheckInList(String[]info, String customerName)throws IOException{
        if (info[8].equals("Checked In")){
            this.CustomerData.add(new Customer(this.i, Integer.parseInt(info[0]), info[1], customerName,info[3],info[4],info[5],info[6],info[7],info[8]));
            this.i++;
        }
    }

    public void reset(){
        this.nameTBox.setText("");
        this.phoneNumberTBox.setText("");
        this.roomNumberTBox.setText("");
        this.checkOutList.setSelected(false);
        this.checkInList.setSelected(false);
        this.bookedList.setSelected(false);
        this.i=1;
        this.setCellTable();
        this.CustomerData = FXCollections.observableArrayList();
        this.check = " ";
    }

    public void update()throws IOException{
        String line = null;
        String line2 = null;
        ArrayList<String> oldContent2 = new ArrayList<>();
        ArrayList<String> oldContent = new ArrayList<>();
        this.selectedCustomer = this.customerList.getSelectionModel().getSelectedItem();
        System.out.println("selectedCustomer"+this.selectedCustomer);
        System.out.println();
        //reservedRoom.txt
        BufferedReader reader2 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/reserveRoom.txt"));
        while ((line2 = reader2.readLine()) != null ){
            String [] info2 = line2.split(",");
            int selectedRoomNo = this.selectedCustomer.getRoomNo();
            String selectedDateIn = this.selectedCustomer.getDateIn();
            String selectedDateOut = this.selectedCustomer.getDateOut();
            System.out.println(info2[0]);
            System.out.println("selected customer roomNo: "+this.selectedCustomer.getRoomNo());
            System.out.println("selected customer dateIn: "+this.selectedCustomer.getDateIn());
            System.out.println("selected customer dateOut: "+this.selectedCustomer.getDateOut());
            if (Integer.parseInt(info2[0])== selectedRoomNo && info2[3].equals(selectedDateIn) && info2[3].equals(selectedDateOut)){
                System.out.println("check value: "+ check);
                if (this.check.equals("Check In")){
                    info2[5]="Checked In";;
                }
                if (this.check.equals("Check Out")){
                    info2[5]="Checked Out";
                }
                if (this.check.equals("Cancel")){
                    info2[5]="Cancelled";
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(info2[0]).append(",");
            sb2.append(info2[1]).append(",");
            sb2.append(info2[2]).append(",");
            sb2.append(info2[3]).append(",");
            sb2.append(info2[4]).append(",");
            sb2.append(info2[5]).append(",");
            sb2.append(info2[6]);

            oldContent2.add(String.valueOf(sb2));
            System.out.println("oldContent2: "+oldContent2);
        }
        File file2 = new File("src/main/resources/com/example/demo/TextFiles/reserveRoom.txt");
        FileWriter writer2 = new FileWriter(file2);
        BufferedWriter br2 = new BufferedWriter(writer2);
        for (String s : oldContent2) {
            br2.write(s);
            br2.newLine();
        }
        br2.close();

        //customerList.txt
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
        while ((line = reader.readLine()) != null ){
            String [] info = line.split(",");
            int selectedRoomNo = this.selectedCustomer.getRoomNo();
            String selectedDateIn = this.selectedCustomer.getDateIn();
            String selectedDateOut = this.selectedCustomer.getDateOut();
            System.out.println(info[0]);
            System.out.println("selected customer roomNo: "+this.selectedCustomer.getRoomNo());
            System.out.println("selected customer dateIn: "+this.selectedCustomer.getDateIn());
            System.out.println("selected customer dateOut: "+this.selectedCustomer.getDateOut());
            if (Integer.parseInt(info[0])== selectedRoomNo && info[4].equals(selectedDateIn) && info[5].equals(selectedDateOut)){
                System.out.println("check value: "+ check);
                if (this.check.equals("Check In")){
                    info[8]="Checked In";
                    info[6]= LocalDate.now().format(this.formatter);
                }
                if (this.check.equals("Check Out")){
                    info[8]="Checked Out";
                    info[7]= LocalDate.now().format(this.formatter);
                }
                if (this.check.equals("Cancel")){
                    info[8]="Cancelled";
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(info[0]).append(",");
            sb.append(info[1]).append(",");
            sb.append(info[2]).append(",");
            sb.append(info[3]).append(",");
            sb.append(info[4]).append(",");
            sb.append(info[5]).append(",");
            sb.append(info[6]).append(",");
            sb.append(info[7]).append(",");
            sb.append(info[8]);

            oldContent.add(String.valueOf(sb));
            System.out.println("oldContent: "+oldContent);
        }


        File file3 = new File("src/main/resources/com/example/demo/TextFiles/customerList.txt");
        FileWriter writer3 = new FileWriter(file3);
        BufferedWriter br3 = new BufferedWriter(writer3);
        for (String s : oldContent) {
            br3.write(s);
            br3.newLine();
        }
        br3.close();

        this.customerList.getItems().clear();
        this.i =1;
        this.loadData();
    }

    private void CustomerMenuValidation(int x, int y){
        if (this.getTemp().equals("Checked In")) {
            this.customerList.setContextMenu(this.contextMenu1);
            this.contextMenu1.show(this.customerList, x, y);
        }else if (this.getTemp().equals("Booked")){
            this.customerList.setContextMenu(this.contextMenu2);
            this.contextMenu2.show(this.customerList, x, y);
        }else{
            this.customerList.setContextMenu(this.contextMenu3);
            this.contextMenu3.show(this.customerList,x,y);
        }
    }
    private void CustomerMenuItem(){
        this.customerList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            public void handle(MouseEvent mouseClick){
                CustomerListController.this.temp = CustomerListController.this.customerList.getSelectionModel().getSelectedItem().status;
                if (mouseClick.getButton() == MouseButton.SECONDARY){
                    CustomerListController.this.CustomerMenuValidation(CustomerListController.this.locationX(), CustomerListController.this.locationY());
                }
            }
        });
        this.checkIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                CustomerListController.this.selectedCustomer =CustomerListController.this.customerList.getSelectionModel().getSelectedItem();
                CustomerListController.this.check = "Check In";
                System.out.println("Check In");
                try {
                    CustomerListController.this.update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Customer has been Checked In");
                alert.showAndWait();
            }
        });
        this.checkOut.setOnAction(new EventHandler<ActionEvent>() {
            String line = null;
            @Override
            public void handle(ActionEvent event) {
                CustomerListController.this.selectedCustomer = CustomerListController.this.customerList.getSelectionModel().getSelectedItem();
                CustomerListController.this.check = "Check Out";
                try {
                    CustomerListController.this.update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                while (true) {
                    try {
                        if ((line = reader.readLine()) == null) break;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    String[] info = line.split(",");
                    if (Integer.parseInt(info[0]) == CustomerListController.this.selectedCustomer.getRoomNo() && info[4].equals(CustomerListController.this.selectedCustomer.getDateIn()) && info[5].equals(CustomerListController.this.selectedCustomer.getDateOut())){
                        customerID = Integer.parseInt(info[2]);
                        roomNO = Integer.parseInt(info[0]);
                        dateIN = info[4];
                        dateOUT = info[5];
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("PopUpPayment.fxml"));
                        PopUpPaymentController popuppaymentcontroller = Loader.getController();

                        try {
                            Loader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Parent p = (Parent)Loader.getRoot();
                        Stage stage2 = new Stage();
                        stage2.setScene(new Scene(p));
                        stage2.setTitle("Payment Method");
                        stage2.show();
                        break;
                    }
                }
            }
        });
        this.cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CustomerListController.this.selectedCustomer =CustomerListController.this.customerList.getSelectionModel().getSelectedItem();
                CustomerListController.this.check = "Cancel";
                try {
                    CustomerListController.this.update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Booking has been cancelled");
                alert.showAndWait();
            }
        });
        this.receipt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    receipt();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public int locationX(){
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();
        int x = (int) point.getX();
        return x;
    }

    public int locationY(){
        PointerInfo pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();
        int y = (int) point.getY();
        return y;
    }

    public void receipt()throws IOException{
        String line = null;
        String line2 = null;
        String line3 = null;
        String customerName = null;
        String phoneNumber= null;
        String emailAddress= null;
        CustomerListController.this.selectedCustomer = CustomerListController.this.customerList.getSelectionModel().getSelectedItem();
        BufferedReader reader3 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
        while ((line3 = reader3.readLine())!=null) {
            String[] info3 = line3.split(",");
            if (Integer.parseInt(info3[0]) == CustomerListController.this.selectedCustomer.getRoomNo() && info3[4].equals(CustomerListController.this.selectedCustomer.getDateIn()) && info3[5].equals(CustomerListController.this.selectedCustomer.getDateOut())) {
                customerID = Integer.parseInt(info3[2]);
                roomNO = Integer.parseInt(info3[0]);
                dateIN = info3[4];
                dateOUT = info3[5];
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Receipt.txt"));
        while ((line = reader.readLine()) != null ) {
            String[] info = line.split(",");
            if (Integer.parseInt(info[0]) == CustomerListController.customerID && Integer.parseInt(info[1]) == CustomerListController.roomNO && info[3].equals(CustomerListController.dateIN) && info[4].equals(CustomerListController.dateOUT)) {
                BufferedReader reader2 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
                while ((line2 = reader2.readLine()) != null ) {
                    String[] info2 = line2.split(",");
                    if (Integer.parseInt(info2[0]) == CustomerListController.customerID) {
                        customerName = info2[2];
                        phoneNumber = info2[3];
                        emailAddress = info2[5];
                        break;
                    }
                }
                ReceiptController.receiptData.add(new Receipt(customerName, phoneNumber,emailAddress,info[11],info[1], info[2], info[3],info[4],info[5],info[6],info[7],info[8],info[9],info[10]));
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("receipt.fxml"));
                ReceiptController receiptController = Loader.getController();

                try {
                    Loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Parent p = (Parent)Loader.getRoot();
                Stage stage2 = new Stage();
                stage2.setScene(new Scene(p));
                String imagePath = "hotel logo transparent.png";
                Image icon = new Image(imagePath);
                stage2.getIcons().add(icon);
                stage2.setTitle("Receipt");
                stage2.setResizable(false);
                stage2.show();
            }
        }
    }

    private void showCustomerInfo(){
        this.customerList.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2){
                CustomerListController.dummyPhoneNumber1 =CustomerListController.this.customerList.getSelectionModel().getSelectedItem().phoneNumber1;
                System.out.println(CustomerListController.dummyPhoneNumber1.getValue());
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(this.getClass().getResource("CustomerInfo.fxml"));
                try {
                    Loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Parent p = (Parent)Loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.setTitle("Customer Information ");
                stage.show();
            }
        });
    }
}
