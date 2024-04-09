package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer {
    public SimpleIntegerProperty customerNo = new SimpleIntegerProperty();
    public SimpleIntegerProperty roomNo = new SimpleIntegerProperty();
    public SimpleStringProperty reservedTime = new SimpleStringProperty();
    public SimpleStringProperty name = new SimpleStringProperty();
    public SimpleStringProperty phoneNumber1 = new SimpleStringProperty();
    public LocalDate dateInTemp;
    public LocalDate dateOutTemp;
    public LocalDate actualDateInTemp;
    public LocalDate actualDateOutTemp;
    public SimpleStringProperty dateIn = new SimpleStringProperty();
    public SimpleStringProperty dateOut = new SimpleStringProperty();
    public SimpleStringProperty actualDateIn = new SimpleStringProperty();
    public SimpleStringProperty actualDateOut = new SimpleStringProperty();
    public SimpleStringProperty status = new SimpleStringProperty();
    public String username;
    public SimpleStringProperty testing = new SimpleStringProperty();
    public DateTimeFormatter formatterTemp =DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public Customer(int customerNo, int roomNo, String reservedTime, String name, String phoneNumber1, String dateIn, String dateOut,String actualDateIn, String actualDateOut, String status){
        this.customerNo.set(customerNo);
        this.roomNo.set(roomNo);
        this.reservedTime.set(reservedTime);
        this.name.set(name);
        this.phoneNumber1.set(phoneNumber1);
        this.status.set(status);
        if (dateIn != null && !dateIn.trim().isEmpty()) {
            this.dateInTemp = LocalDate.parse(dateIn, formatterTemp);
            this.dateIn.set(this.dateInTemp.format(formatter));
        }
        if (dateOut != null && !dateOut.trim().isEmpty()){
            this.dateOutTemp =LocalDate.parse(dateOut, formatterTemp);
            this.dateOut.set(this.dateOutTemp.format(formatter));
        }
        if (actualDateIn != null && !actualDateIn.trim().isEmpty()){
            this.actualDateInTemp =LocalDate.parse(actualDateIn, formatterTemp);
            this.actualDateIn.set(this.actualDateInTemp.format(formatter));
        }
        if (actualDateOut != null && !actualDateOut.trim().isEmpty()){
            this.actualDateOutTemp =LocalDate.parse(actualDateOut, formatterTemp);
            this.actualDateOut.set(this.actualDateOutTemp.format(formatter));
        }
    }

    public int getCustomerNo() {
        return customerNo.get();
    }

    public SimpleIntegerProperty customerNoProperty() {
        return customerNo;
    }

    public void setCustomerNo(int customerNo) {
        this.customerNo.set(customerNo);
    }

    public int getRoomNo() {
        return roomNo.get();
    }

    public SimpleIntegerProperty roomNoProperty() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo.set(roomNo);
    }

    public String getReservedTime() {
        return reservedTime.get();
    }

    public SimpleStringProperty reservedTimeProperty() {
        return reservedTime;
    }

    public void setReservedTime(String reservedTime) {
        this.reservedTime.set(reservedTime);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhoneNumber1() {
        return phoneNumber1.get();
    }

    public SimpleStringProperty phoneNumber1Property() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1.set(phoneNumber1);
    }

    public LocalDate getDateInTemp() {
        return dateInTemp;
    }

    public void setDateInTemp(LocalDate dateInTemp) {
        this.dateInTemp = dateInTemp;
    }

    public LocalDate getDateOutTemp() {
        return dateOutTemp;
    }

    public void setDateOutTemp(LocalDate dateOutTemp) {
        this.dateOutTemp = dateOutTemp;
    }

    public LocalDate getActualDateInTemp() {
        return actualDateInTemp;
    }

    public void setActualDateInTemp(LocalDate actualDateInTemp) {
        this.actualDateInTemp = actualDateInTemp;
    }

    public LocalDate getActualDateOutTemp() {
        return actualDateOutTemp;
    }

    public void setActualDateOutTemp(LocalDate actualDateOutTemp) {
        this.actualDateOutTemp = actualDateOutTemp;
    }

    public String getDateIn() {
        return dateIn.get();
    }

    public SimpleStringProperty dateInProperty() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn.set(dateIn);
    }

    public String getDateOut() {
        return dateOut.get();
    }

    public SimpleStringProperty dateOutProperty() {
        return dateOut;
    }

    public void setDateOut(String dateOut) {
        this.dateOut.set(dateOut);
    }

    public String getActualDateIn() {
        return actualDateIn.get();
    }

    public SimpleStringProperty actualDateInProperty() {
        return actualDateIn;
    }

    public void setActualDateIn(String actualDateIn) {
        this.actualDateIn.set(actualDateIn);
    }

    public String getActualDateOut() {
        return actualDateOut.get();
    }

    public SimpleStringProperty actualDateOutProperty() {
        return actualDateOut;
    }

    public void setActualDateOut(String actualDateOut) {
        this.actualDateOut.set(actualDateOut);
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTesting() {
        return testing.get();
    }

    public SimpleStringProperty testingProperty() {
        return testing;
    }

    public void setTesting(String testing) {
        this.testing.set(testing);
    }
}
