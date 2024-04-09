package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.demo.RoomController.cost;
import static com.example.demo.RoomController.roomNo;

public class PopUpRoomController implements Initializable {
    @FXML
    private Spinner<Integer> extraBedSpinner;
    @FXML
    private Spinner<Integer> personSpinner;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonAdd;

    public int totalCostExtraBed = 0;
    public int totalCharge = 0;
    public int totalCharge_AfterTax = 0;

    public double serviceCharge = 0;
    public int tourismTax = 10;
    public double totalAmount = 0;


    public void Cancel(ActionEvent event){
        Stage stage = (Stage)this.buttonCancel.getScene().getWindow();
        stage.close();
    }
    public void Add(ActionEvent event)throws IOException {
        Reserve.setExtraBed(this.extraBedSpinner.getValue());
        Reserve.setPersonPerRoom(this.personSpinner.getValue());
        if (RoomController.days_difference == 0){
            RoomController.days_difference = 1;
        }
        this.addTotal();
        this.addAllValue();
        this.buttonAdd.getScene().getWindow().hide();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Reserve.fxml")));
        Scene scene = new Scene(reserve);
        LogInPage.primaryStage.setScene(scene);
    }

    public Spinner<Integer> getExtraBedSpinner() {
        return extraBedSpinner;
    }

    public void setExtraBedSpinner(Spinner<Integer> extraBedSpinner) {
        this.extraBedSpinner = extraBedSpinner;
    }

    public Spinner<Integer> getPersonSpinner() {
        return personSpinner;
    }

    public void setPersonSpinner(Spinner<Integer> personSpinner) {
        this.personSpinner = personSpinner;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.addSpinnerValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSpinnerValue()throws IOException{
        String line = null;
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Room.txt"));
        while ((line = reader.readLine()) != null){
            String[] info = line.split(",");
            if(info[0].equals(Integer.toString(roomNo))){
                this.extraBedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.parseInt(info[4])));
                this.personSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.parseInt(info[3])+Integer.parseInt(info[4])));
                break;
            }
        }
    }

    public void addTotal(){

        totalCostExtraBed =this.extraBedSpinner.getValue()*50;
        totalCharge = totalCostExtraBed + cost * (int)RoomController.days_difference;
        serviceCharge = totalCharge*0.1;
        tourismTax = tourismTax * (int)RoomController.days_difference;
        System.out.println("tourismTax"+tourismTax);
        totalAmount = totalCharge+serviceCharge+tourismTax;


        System.out.println("convert day difference to int"+(int)RoomController.days_difference);
    }

    public void addAllValue(){
        String extraBedCost = "x 50";
        String costPerDay = "x "+ cost;
        ReserveController.ReserveData.add(new RoomReservation(RoomController.roomNo, RoomController.roomType, (int)RoomController.days_difference,costPerDay, this.extraBedSpinner.getValue(),this.personSpinner.getValue(), extraBedCost, RoomController.stringDateIn, RoomController.stringDateOut, totalCharge, serviceCharge,tourismTax,totalAmount));
        System.out.println(ReserveController.ReserveData.get(0).dateIn);
    }
}
