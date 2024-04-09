package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReserveController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField ic_number;
    @FXML
    private TextField phone_number_1;
    @FXML
    private TextField phone_number_2;
    @FXML
    private TextField email_address;
    @FXML
    private CheckBox immediateCheckInBtn;
    @FXML
    private Label vUsername;
    @FXML
    private Label vIc_number;
    @FXML
    private Label vPhone_number_1;
    @FXML
    private Label vPhone_number_2;
    @FXML
    private Label label_username;

    @FXML
    private Label vEmailAddress;

    int selectedIndex;
    int rowCount;
    public int customerIdIndex;
    public int roomIdIndex;
    public int totalPeople = 0;
    public int totalAmount = 0;
    public String formattedTime;
    Reserve customerList = new Reserve();
    String status = null;
    int flag = 1;

    int flag2;
    public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private TableView <RoomReservation> reserveList;
    @FXML
    private TableColumn<?,?> columnRoomNo;
    @FXML
    private TableColumn<?,?> columnRoomType;
    @FXML
    private TableColumn<?,?> columnDays;
    @FXML
    private TableColumn<?,?> columnCost;
    @FXML
    private TableColumn<?,?> columnExtraBed;
    @FXML
    private TableColumn<?,?> columnBedValue;
    @FXML
    private TableColumn<?,?> columnPeople;
    @FXML
    private TableColumn<?,?> columnCheckInDate;
    @FXML
    private TableColumn<?,?> columnCheckOutDate;
    @FXML
    private TableColumn<?,?> columnTotal;
    @FXML
    public static ObservableList<RoomReservation> ReserveData = FXCollections.observableArrayList();

    public ReserveController(){
    }

    @FXML
    public void goToCustomerList(ActionEvent event) throws IOException {
        this.setCustomerData();
        Parent customerList = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CustomerList.fxml")));
        Scene scene = new Scene(customerList);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToReserve(ActionEvent event) throws IOException{
        this.setCustomerData();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Reserve.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goToRoom(ActionEvent event)throws IOException{
        this.setCustomerData();
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

    public boolean phoneNumberValidate(String phoneNumber){
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return (m.matches());
    }

    public boolean icNumberValidate(String icNumber){
        Pattern p = Pattern.compile("^\\d{12}$");
        Matcher m = p.matcher(icNumber);
        return (m.matches());
    }

    public boolean emailValidate(String emailAddress){
        Pattern p = Pattern.compile("^(.+)@(\\S+)$");
        Matcher m = p.matcher(emailAddress);
        return (m.matches());
    }

    public int checkPhoneNumber1(String checker)throws IOException{
        int exist = 0;
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null ) {
            String[] info = line.split(",");
            if (checker.equals(info[3])||checker.equals(info[4])){
                exist = 1;
                break;
            }
        }
        return exist;
    }

    public int checkPhoneNumber2(String checker)throws IOException{
        int exist1 = 0;
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null ) {
            String[] info = line.split(",");
            if (checker.equals(info[4])||checker.equals(info[3])){
                exist1 = 1;
                break;
            }
        }
        return exist1;
    }
    public int checkEmail(String checker)throws IOException{
        int exist2 = 0;
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null ) {
            System.out.println(line);
            String[] info = line.split(",");
            System.out.println(Arrays.toString(info));
            if (checker.equals(info[5])){
                exist2 = 1;
                break;
            }
        }
        return exist2;
    }



    public void validation4(int exist, int exist1, int exist2){
        this.vPhone_number_1.setText(" ");
        this.vEmailAddress.setText(" ");
        this.vPhone_number_2.setText(" ");
        if (exist == 1){
            this.vPhone_number_1.setText("Enter Another Phone Number!");
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Phone Number 1 Has Been Taken!");
            alert.showAndWait();
        }
        if (exist1 == 1){
            this.vPhone_number_2.setText("Enter Another Phone Number!");
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Phone Number 2 Has Been Taken!");
            alert.showAndWait();
        }
        if (exist2 == 1){
            this.vEmailAddress.setText("Enter Another Email Address!");
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Email Address Has Been Taken!");
            alert.showAndWait();
        }
    }
    private void validation3(){
        this.vIc_number.setText(" ");
        this.vPhone_number_1.setText(" ");
        this.vEmailAddress.setText(" ");
        this.vPhone_number_2.setText(" ");

        if (!this.icNumberValidate(this.ic_number.getText())){
            this.vIc_number.setText("Invalid IC Number!");
        }

        if (!this.phoneNumberValidate(this.phone_number_1.getText())){
            this.vPhone_number_1.setText("Invalid Phone Number!");
        }

        if (!this.email_address.getText().isEmpty()){
            if (!this.emailValidate(this.email_address.getText())){
                this.vEmailAddress.setText("Invalid Email Format!");
            }
        }

        if (!this.phone_number_2.getText().isEmpty()){
            if (!this.phoneNumberValidate(this.phone_number_2.getText())){
                this.vPhone_number_2.setText("Invalid Phone Number");
            }
        }


    }

    private void saveData(int exist, int exist1 ,int exist2, ActionEvent event)throws IOException{
        String line = null;
        int customerId = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] customer = line.split(",");
            if(this.ic_number.getText().equals(customer[1])){
                flag = 0;
                customerId = Integer.parseInt(customer[0]);
                System.out.println("customerId"+customerId);
                break;
            } else {
                flag = 1;
            }
        }
        this.customerList.setUsername(this.username.getText());
        this.customerList.setIc_number(this.ic_number.getText());
        if (this.email_address.getText()==null||this.email_address.getText().isEmpty() || this.email_address.getText().equals("null")){
            System.out.println("email address: "+ this.email_address.getText());
        }else {
            this.customerList.setEmail_address(this.email_address.getText());
        }
        this.customerList.setPhone_number_1(this.phone_number_1.getText());
        System.out.println("phone number 2: "+this.phone_number_2.getText());
        this.customerList.setPhone_number_2(this.phone_number_2.getText());
        if (flag == 1){
            if (this.email_address.getText()==null||this.email_address.getText().isEmpty() || this.email_address.getText().equals("null")){
                System.out.println("Email address is empty");
            }else {
                exist2 = checkEmail(this.email_address.getText());
            }
            exist = checkPhoneNumber1(this.phone_number_1.getText());
            if (this.phone_number_2.getText()==null ||this.phone_number_2.getText().isEmpty() || this.phone_number_2.getText().equals("null")){
                System.out.println("Phone number 2 is empty");
            }else {
                exist1 = checkPhoneNumber2(this.phone_number_2.getText());
            }
            if (exist == 0 && exist1 == 0 && exist2 == 0){
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                alert2.setTitle("Confirmation Dialog");
                alert2.setHeaderText(null);
                alert2.setContentText("Are you sure you want to add the booking? ");
                int finalCustomerId = customerId;
                alert2.showAndWait().ifPresent((type) -> {
                    if (type == ButtonType.CANCEL) {
                        event.consume();
                    } else if (type == ButtonType.OK) {
                        System.out.println("flag" + flag);
                        try {
                            this.getAllRoom(finalCustomerId);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append(this.customerIdIndex).append(",");
                        sb.append(this.customerList.getIc_number()).append(",");
                        sb.append(this.customerList.getUsername()).append(",");
                        sb.append(this.customerList.getPhone_number_1()).append(",");
                        sb.append(this.customerList.getPhone_number_2()).append(",");
                        sb.append(this.customerList.getEmail_address());
                        File file = new File("src/main/resources/com/example/demo/TextFiles/customer.txt");
                        FileWriter writer = null;
                        try {
                            writer = new FileWriter(file,true);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        BufferedWriter br = new BufferedWriter(writer);
                        try {
                            br.write(String.valueOf(sb));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            br.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            br.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText((String)null);
                        alert.setContentText("Customer has been added");
                        alert.showAndWait();
                        this.clearData();
                    }
                });
            }else {
                this.validation4(exist,exist1,exist2);
            }
        }else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Are you sure you want to add the booking? ");
            int finalCustomerId1 = customerId;
            alert2.showAndWait().ifPresent((type) -> {
                if (type == ButtonType.CANCEL) {
                    event.consume();
                } else if (type == ButtonType.OK) {
                    System.out.println("flag" + flag);
                    try {
                        this.getAllRoom(finalCustomerId1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    this.clearData();
                }
            });
        }
    }

    @FXML
    private void confirm (ActionEvent event)throws IOException {
        int exist = 0;
        int exist1 = 0;
        int exist2 = 0;
        if (this.username.getText() != null && this.ic_number.getText() != null && (this.phone_number_1 != null || this.phone_number_2 != null)) {
            if (!this.username.getText().isEmpty() && !this.ic_number.getText().isEmpty() && !this.phone_number_1.getText().isEmpty() && (this.phone_number_2.getText()==null || this.phone_number_2.getText().isEmpty() || this.phone_number_2.getText().equals("null")) && (this.email_address.getText()==null || this.email_address.getText().isEmpty() ||this.email_address.getText().equals("null")) && !ReserveData.isEmpty()) {
                System.out.println("validate 1");
                if (this.phoneNumberValidate(this.phone_number_1.getText())&& this.icNumberValidate(this.ic_number.getText())){
                    this.saveData(exist,exist1,exist2,event);
                }else {
                    this.validation3();
                }
            }else if(!this.username.getText().isEmpty() && !this.ic_number.getText().isEmpty() && !this.phone_number_1.getText().isEmpty() && (!(this.phone_number_2.getText() ==null) || !this.phone_number_2.getText().isEmpty()) && (this.email_address.getText()==null || this.email_address.getText().isEmpty() || this.email_address.getText().equals("null")) && !ReserveData.isEmpty()){
                System.out.println("validate 2");
                if (this.phoneNumberValidate(this.phone_number_1.getText())&& this.icNumberValidate(this.ic_number.getText()) && this.phoneNumberValidate(this.phone_number_2.getText())){
                    this.saveData(exist,exist1,exist2,event);
                }else {
                    this.validation3();
                }
            }else if(!this.username.getText().isEmpty() && !this.ic_number.getText().isEmpty() && !this.phone_number_1.getText().isEmpty() && (this.phone_number_2.getText()==null ||this.phone_number_2.getText().isEmpty() || this.phone_number_2.getText().equals("null")) && (!(this.email_address.getText() ==null)|| !this.email_address.getText().isEmpty()) && !ReserveData.isEmpty()){
                System.out.println("validate 3");
                if (this.phoneNumberValidate(this.phone_number_1.getText())&& this.icNumberValidate(this.ic_number.getText()) && this.emailValidate(this.email_address.getText())){
                    this.saveData(exist,exist1,exist2,event);
                }else {
                    this.validation3();
                }
            }else if (!this.username.getText().isEmpty() && !this.ic_number.getText().isEmpty() && !this.phone_number_1.getText().isEmpty() && (!(this.phone_number_2.getText() ==null) || !this.phone_number_2.getText().isEmpty()) && (!(this.email_address.getText() ==null)|| !this.email_address.getText().isEmpty()) && !ReserveData.isEmpty()){
                System.out.println("validate 4");
                if (this.phoneNumberValidate(this.phone_number_1.getText())&& this.icNumberValidate(this.ic_number.getText()) && this.phoneNumberValidate(this.phone_number_2.getText()) && this.emailValidate(this.email_address.getText())){
                    this.saveData(exist,exist1,exist2,event);
                }else {
                    this.validation3();
                }
            }else{
                this.validation();
            }
        }else {
            this.validation2();
        }
    }
    @FXML
    private void autofill (KeyEvent event) throws IOException {
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null){
            String [] customer = line.split(",");
            System.out.println(ic_number.getText());
            System.out.println("customer list: "+ customer[1]);
            if(this.ic_number.getText().equals(customer[1])){
                this.username.setText(customer[2]);
                username.setEditable(false);
                this.phone_number_1.setText(customer[3]);
                phone_number_1.setEditable(false);
                this.phone_number_2.setText(customer[4]);
                phone_number_2.setEditable(false);
                this.email_address.setText(customer[5]);
                email_address.setEditable(false);
                break;
            }else {
                this.username.clear();
                username.setEditable(true);
                this.phone_number_1.clear();
                phone_number_1.setEditable(true);
                this.phone_number_2.clear();
                phone_number_2.setEditable(true);
                this.email_address.clear();
                email_address.setEditable(true);
            }
        }
    }

    @FXML
    private void addRoom(ActionEvent event)throws IOException{
        this.setCustomerData();
        Parent room = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Room.fxml")));
        Scene scene = new Scene(room);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void cancelRoom(ActionEvent event)throws IOException{
        this.selectedIndex = this.reserveList.getSelectionModel().getSelectedIndex();
        this.reserveList.getItems().remove(this.reserveList.getSelectionModel().getSelectedItem());
        if(this.selectedIndex >= 0){
            ReserveData.remove(this.selectedIndex);
        }
        if (this.selectedIndex < 0){
            ReserveData.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setTextBox();
        this.reserveList.setEditable(false);
        this.setReserveCellTable();
        this.loadRoomData();
        this.rowCount = this.reserveList.getItems().size();
        this.getCurrentTime();
        System.out.println("refresh");

    }

    private void setReserveCellTable(){
        this.columnRoomNo.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        this.columnRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        this.columnDays.setCellValueFactory(new PropertyValueFactory<>("days"));
        this.columnCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        this.columnExtraBed.setCellValueFactory(new PropertyValueFactory<>("extraBed"));
        this.columnBedValue.setCellValueFactory(new PropertyValueFactory<>("bedValue"));
        this.columnPeople.setCellValueFactory(new PropertyValueFactory<>("noOfPeople"));
        this.columnCheckInDate.setCellValueFactory(new PropertyValueFactory<>("dateIn"));
        this.columnCheckOutDate.setCellValueFactory(new PropertyValueFactory<>("dateOut"));
        this.columnTotal.setCellValueFactory(new PropertyValueFactory<>("totalCharges"));

    }

    private void loadCustomerID() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        LineNumberReader lnr = new LineNumberReader(reader);
        lnr.skip(Long.MAX_VALUE);
        this.customerIdIndex =lnr.getLineNumber() + 1;
        System.out.println("customerId: "+customerIdIndex);
    }

    private void loadReservationID()throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/reserve_detail.txt"));
        LineNumberReader lnr = new LineNumberReader(reader);
        lnr.skip(Long.MAX_VALUE);
        this.roomIdIndex =lnr.getLineNumber()+1;
        System.out.println("reservationId: "+ roomIdIndex);
    }

    private void getCurrentTime(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        this.formattedTime =df.format(now);
        System.out.println(formattedTime);
    }

    private void getAllRoom(int customerId)throws IOException{
        System.out.println("test");
        System.out.println("rowCount: "+rowCount);
        if(this.immediateCheckInBtn.isSelected()){
            this.status = "Checked In";
        }else{
            this.status = "Booked";
        }

        if (this.rowCount >= 1) {
            this.loadCustomerID();
            System.out.println("bf"+customerIdIndex);
            System.out.println("bf customerId"+ customerId);
            if (flag == 0){
                System.out.println("flag2" + flag);
                this.customerIdIndex = customerId;
            }
            System.out.println("at"+customerIdIndex);
            System.out.println("load"+customerIdIndex);
            for (int a = 0; a <= this.rowCount-1; a++) {
                this.loadReservationID();
                this.totalPeople += ReserveData.get(a).getNoOfPeople();
                this.totalAmount += ReserveData.get(a).getTotalCharges();

                StringBuilder sb = new StringBuilder();
                sb.append(this.roomIdIndex).append(",");
                sb.append(this.customerIdIndex).append(",");
                sb.append(this.formattedTime).append(",");
                sb.append(this.totalPeople).append(",");
                sb.append(this.totalAmount);
                System.out.println(sb);
                File file = new File("src/main/resources/com/example/demo/TextFiles/reserve_detail.txt");
                FileWriter writer = new FileWriter(file,true);
                BufferedWriter br = new BufferedWriter(writer);
                br.write(String.valueOf(sb));
                br.newLine();
                br.close();

                StringBuilder sb2 = new StringBuilder();
                sb2.append(ReserveData.get(a).getRoomNo()).append(",");
                sb2.append(ReserveData.get(a).getExtraBed()).append(",");
                sb2.append(ReserveData.get(a).getDateIn()).append(",");
                sb2.append(ReserveData.get(a).getDateOut()).append(",");
                sb2.append(ReserveData.get(a).getNoOfPeople()).append(",");
                sb2.append(this.status).append(",");
                sb2.append(this.roomIdIndex);
                System.out.println(sb2);
                File file2 = new File("src/main/resources/com/example/demo/TextFiles/reserveRoom.txt");
                FileWriter writer2 = new FileWriter(file2,true);
                BufferedWriter br2 = new BufferedWriter(writer2);
                br2.write(String.valueOf(sb2));
                br2.newLine();
                br2.close();

                StringBuilder sb3 = new StringBuilder();
                sb3.append(ReserveData.get(a).getRoomNo()).append(",");
                sb3.append(this.formattedTime).append(",");
                sb3.append(this.customerIdIndex).append(",");
                sb3.append(customerList.getPhone_number_1()).append(",");
                sb3.append(ReserveData.get(a).getDateIn()).append(",");
                sb3.append(ReserveData.get(a).getDateOut()).append(",");
                if(this.status.equals("Checked In")){
                    sb3.append(LocalDate.now().format(this.formatter)).append(",");
                    sb3.append(",");
                }else {
                    sb3.append(","+",");
                }
                sb3.append(this.status);
                System.out.println(sb3);
                File file3 = new File("src/main/resources/com/example/demo/TextFiles/customerList.txt");
                FileWriter writer3 = new FileWriter(file3,true);
                BufferedWriter br3 = new BufferedWriter(writer3);
                br3.write(String.valueOf(sb3));
                br3.newLine();
                br3.close();

                StringBuilder sb4 = new StringBuilder();
                sb4.append(this.customerIdIndex).append(",");
                sb4.append(ReserveData.get(a).getRoomNo()).append(",");
                sb4.append(ReserveData.get(a).getExtraBed()).append(",");
                sb4.append(ReserveData.get(a).getDateIn()).append(",");
                sb4.append(ReserveData.get(a).getDateOut()).append(",");
                sb4.append(this.totalPeople).append(",");
                LocalDate startDate = LocalDate.parse(ReserveData.get(a).getDateIn());
                LocalDate endDate = LocalDate.parse(ReserveData.get(a).getDateOut());
                long days_difference = DAYS.between(startDate, endDate);
                if (days_difference == 0){
                    days_difference = 1;
                }
                sb4.append(days_difference).append(",");
                sb4.append(this.totalAmount).append(",");
                sb4.append(ReserveData.get(a).getServiceCharge()).append(",");
                sb4.append(ReserveData.get(a).getTourismTax()).append(",");
                sb4.append(ReserveData.get(a).getTotalAmount()).append(",");
                sb4.append("null");
                System.out.println("Receipt: "+ sb4);
                File file4 = new File("src/main/resources/com/example/demo/TextFiles/Receipt.txt");
                FileWriter writer4 = new FileWriter(file4,true);
                BufferedWriter br4 = new BufferedWriter(writer4);
                br4.write(String.valueOf(sb4));
                br4.newLine();
                br4.close();
            }

        }
    }


    private void loadRoomData(){
        this.reserveList.getItems().clear();
        this.reserveList.getItems().addAll(ReserveData);
    }


    public void setCustomerData(){
        Reserve.customerList.setUsername(this.username.getText());
        Reserve.customerList.setIc_number(this.ic_number.getText());
        Reserve.customerList.setPhone_number_1(this.phone_number_1.getText());
        Reserve.customerList.setPhone_number_2(this.phone_number_2.getText());
        Reserve.customerList.setEmail_address(this.email_address.getText());
    }

    public void setTextBox(){
        this.username.setText(Reserve.customerList.getUsername());
        this.ic_number.setText(Reserve.customerList.getIc_number());
        this.phone_number_1.setText(Reserve.customerList.getPhone_number_1());
        this.phone_number_2.setText(Reserve.customerList.getPhone_number_2());
        this.email_address.setText(Reserve.customerList.getEmail_address());
    }

    private void clearData(){
        this.username.setText(null);
        this.ic_number.setText(null);
        this.phone_number_1.setText(null);
        this.phone_number_2.setText(null);
        this.email_address.setText(null);
        this.vUsername.setText(" ");
        this.vIc_number.setText(" ");
        this.vPhone_number_1.setText(" ");
        this.vPhone_number_2.setText(" ");
        this.reserveList.getItems().clear();
        ReserveData.clear();
        this.immediateCheckInBtn.setSelected(false);
    }

    private void validation(){
        this.flag2 = 0;
        this.vUsername.setText(" ");
        this.vIc_number.setText(" ");
        this.vPhone_number_1.setText(" ");
        if(this.username.getText().isEmpty()){
            this.vUsername.setText("Enter Name!");
        }

        if (this.ic_number.getText().isEmpty()){
            this.vIc_number.setText("Enter IC Number!");
        }

        if (this.phone_number_1.getText().isEmpty()){
            this.vPhone_number_1.setText("Enter Phone Number 1!");
        }

        Alert alert;
        if (ReserveData.isEmpty() && this.username.getText().isEmpty() && this.ic_number.getText().isEmpty() && (this.phone_number_1.getText().isEmpty() || this.phone_number_2.getText().isEmpty())){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Customer and Room Information!");
            alert.showAndWait();
            this.flag2 = 1;
        }

        if(ReserveData.isEmpty() && this.flag2 == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Room Information!");
            alert.showAndWait();
        }
    }

    private void validation2(){
        this.flag2 = 0;
        this.vUsername.setText(" ");
        this.vIc_number.setText(" ");
        this.vPhone_number_1.setText(" ");
        if (this.username.getText()==null ||this.username.getText().isEmpty()){
            this.vUsername.setText("Enter Name!");
        }

        if (this.ic_number.getText() == null|| this.ic_number.getText().isEmpty()){
            this.vIc_number.setText("Enter IC Number!");
        }

        if (this.phone_number_1.getText()== null|| this.phone_number_1.getText().isEmpty()){
            this.vPhone_number_1.setText("Enter Phone Number 1!");
        }

        Alert alert;
        if (ReserveData.isEmpty() && this.username.getText() == null && this.ic_number.getText() == null && (this.phone_number_1.getText() == null || this.phone_number_2.getText() == null)){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Customer and Room Information!");
            alert.showAndWait();
            this.flag2 = 1;
        }

        if(ReserveData.isEmpty() && this.flag2 == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Room Information!");
            alert.showAndWait();
        }
    }
}
