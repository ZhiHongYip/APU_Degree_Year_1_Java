package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomerInfoController extends ReserveController  {
    @FXML
    private TextField customerEmail;

    @FXML
    private TextField customerIcNumber;

    @FXML
    private TextField customerId;

    @FXML
    private Label customerName;

    @FXML
    private TextField phoneNumber1;

    @FXML
    private TextField phoneNumber2;
    @FXML
    private Button updateBtn;

    public int cId;
    public String cIcNumber;
    public String cName;
    public String cPhoneNumber1;
    public String cPhoneNumber2;
    public String cEmail;
    @FXML
    public Button closeBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.loadCustomerData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setTextFields();
    }

    public void Close(ActionEvent event){
        Stage stage = (Stage)this.closeBtn.getScene().getWindow();
        stage.close();
    }
    public void validation(int exist, int exist1, int exist2){
        if (exist == 1){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Phone Number 1 Has Been Taken!");
            alert.showAndWait();
        }
        if (exist1 == 1){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Phone Number 2 Has Been Taken!");
            alert.showAndWait();
        }
        if (exist2 == 1){
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Email Address Has Been Taken!");
            alert.showAndWait();
        }
    }

    public void update (ActionEvent event)throws IOException{
        int exist = 2;
        int exist1 = 2;
        int exist2 = 2;
        ArrayList<String> oldContent2 = new ArrayList<>();
        ArrayList<String> oldContent = new ArrayList<>();
        String line = null;
        String line2 = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null ) {
            String[] info = line.split(",");
            if (this.cIcNumber.equals(info[1]) && this.cId == Integer.parseInt(info[0]) && this.cName.equals(info[2])) {
                System.out.println("Customer Find in Database");
                if (!this.phoneNumber1.getText().equals(info[3])){
                    if (this.phoneNumberValidate(this.phoneNumber1.getText())){
                        exist = checkPhoneNumber1(this.phoneNumber1.getText());
                        if (exist == 2){
                            exist = 0;
                        }
                    }else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Phone Number");
                        alert.showAndWait();
                        break;
                    }

                }else {
                    exist = 0;
                }
                System.out.println("phone number 2:"+info[4]);
                System.out.println(this.phoneNumber2.getText());
                if (!this.phoneNumber2.getText().equals(info[4])){
                    if (this.phoneNumberValidate(this.phoneNumber2.getText())){
                        exist1 = checkPhoneNumber2(this.phoneNumber2.getText());
                        if (exist1 == 2){
                            exist1 = 0;
                        }
                    }else if (this.phoneNumber2.getText().equals("-")||this.phoneNumber2.getText().trim().isEmpty()){
                        exist1 = 0;
                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Phone Number");
                        alert.showAndWait();
                    }
                }else if (this.phoneNumber2.getText().equals(info[4])){
                    exist1 = 0;
                }else {
                    exist1 = 0;
                }
                if (!this.customerEmail.getText().equals(info[5])){
                    System.out.println("CustomerInfo Email Validation 1");
                    if (this.emailValidate(this.customerEmail.getText())){
                        System.out.println("CustomerInfo Email Validation 2");
                        exist2 = checkEmail(this.customerEmail.getText());
                        System.out.println("exist 2:"+exist2);
                        if (exist2 == 2){
                            exist2 = 0;
                        }
                    } else if (this.customerEmail.getText().equals("-")||this.customerEmail.getText().trim().isEmpty()){
                        exist2 = 0;
                    } else {
                        Alert alert;
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Email Address");
                        alert.showAndWait();
                    }
                }else {
                    exist2 = 0;
                }
                System.out.println("exist: "+exist);
                System.out.println("exist1: "+exist1);
                System.out.println("exist2: "+exist2);
                if (exist == 0 && exist1 == 0 && exist2 == 0){
                    info[3] = this.phoneNumber1.getText();
                    if (this.phoneNumber2.getText().equals("-")||this.customerEmail.getText().trim().isEmpty()){
                        info[4] = "";
                    }else {
                        info[4] = this.phoneNumber2.getText();
                    }
                    if (this.customerEmail.getText().equals("-")||this.customerEmail.getText().trim().isEmpty()){
                        info[5]="null";
                    }else {
                        info[5] = this.customerEmail.getText();
                    }
                }else {
                    this.validation(exist,exist1,exist2);
                }

            }
            StringBuilder sb = new StringBuilder();
            sb.append(info[0]).append(",");
            sb.append(info[1]).append(",");
            sb.append(info[2]).append(",");
            sb.append(info[3]).append(",");
            sb.append(info[4]).append(",");
            sb.append(info[5]);

            oldContent.add(String.valueOf(sb));
            System.out.println("oldContent: "+oldContent);
        }

        BufferedReader reader2 = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customerList.txt"));
        while ((line2= reader2.readLine())!= null){
            String[] info2 = line2.split(",");
            if (this.cId == Integer.parseInt(info2[2])) {
                System.out.println("Customer Find in CustomerList");
                if (!this.phoneNumber1.getText().equals(info2[3])){
                    if (this.phoneNumberValidate(this.phoneNumber1.getText())){
                        exist = checkPhoneNumber1(this.phoneNumber1.getText());
                        if (exist == 2){
                            exist = 0;
                        }
                    }
                }
                if (exist == 0){
                    info2[3] = this.phoneNumber1.getText();
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(info2[0]).append(",");
            sb2.append(info2[1]).append(",");
            sb2.append(info2[2]).append(",");
            sb2.append(info2[3]).append(",");
            sb2.append(info2[4]).append(",");
            sb2.append(info2[5]).append(",");
            sb2.append(info2[6]).append(",");
            sb2.append(info2[7]).append(",");
            sb2.append(info2[8]);

            oldContent2.add(String.valueOf(sb2));
            System.out.println("oldContent2: "+oldContent2);
        }

        System.out.println("exist: "+exist);
        System.out.println("exist1: "+exist1);
        System.out.println("exist2: "+exist2);
        if (exist == 0 && exist1 == 0 && exist2 == 0) {
            File file3 = new File("src/main/resources/com/example/demo/TextFiles/customer.txt");
            FileWriter writer3 = new FileWriter(file3);
            BufferedWriter br3 = new BufferedWriter(writer3);
            for (String s : oldContent) {
                br3.write(s);
                br3.newLine();
            }
            br3.close();


            File file = new File("src/main/resources/com/example/demo/TextFiles/customerList.txt");
            FileWriter writer = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(writer);
            for (String s : oldContent2) {
                br.write(s);
                br.newLine();
            }
            br.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText((String)null);
            alert.setContentText("Customer Information Updated");
            alert.showAndWait();
            this.updateBtn.getScene().getWindow().hide();
            Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("CustomerList.fxml")));
            Scene scene = new Scene(reserve);
            LogInPage.primaryStage.setScene(scene);
        }
    }

    private void setTextFields(){
        this.customerName.setText(this.cName);
        this.customerId.setText(String.valueOf(this.cId));
        this.customerIcNumber.setText(this.cIcNumber);
        this.phoneNumber1.setText(this.cPhoneNumber1);
        this.phoneNumber2.setText(this.cPhoneNumber2);
        this.customerEmail.setText(this.cEmail);
    }
    private void loadCustomerData()throws IOException {
        String line = null;

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/customer.txt"));
        while ((line = reader.readLine()) != null ) {
            String[] info = line.split(",");
            System.out.println(Arrays.toString(info));
            if (info[3].equals(CustomerListController.dummyPhoneNumber1.getValue())) {
                this.cId = Integer.parseInt(info[0]);
                this.cIcNumber = info[1];
                this.cName = info[2];
                this.cPhoneNumber1 = info[3];
                if (info[4].equals("")|| info[4].equals("null")){
                    this.cPhoneNumber2 = "-";
                }else {
                    this.cPhoneNumber2 = info[4];
                }
                if (info[5].equals("")||info[5].equals("null")){
                    this.cEmail = "-";
                }else {
                    this.cEmail = info[5];
                }
            }
        }
    }



}
