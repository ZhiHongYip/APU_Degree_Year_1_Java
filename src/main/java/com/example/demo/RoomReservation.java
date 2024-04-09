package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomReservation {
    public SimpleIntegerProperty roomNo = new SimpleIntegerProperty();
    public SimpleStringProperty roomType = new SimpleStringProperty();
    public SimpleIntegerProperty days = new SimpleIntegerProperty();
    public SimpleStringProperty cost = new SimpleStringProperty();
    public SimpleStringProperty dateIn = new SimpleStringProperty();
    public SimpleStringProperty dateOut = new SimpleStringProperty();
    public SimpleIntegerProperty extraBed = new SimpleIntegerProperty();
    public SimpleStringProperty bedValue = new SimpleStringProperty();
    public SimpleIntegerProperty noOfPeople = new SimpleIntegerProperty();
    public SimpleIntegerProperty totalCharges = new SimpleIntegerProperty();
    public double serviceCharge;
    public int tourismTax;
    public double totalAmount;

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(int serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getTourismTax() {
        return tourismTax;
    }

    public void setTourismTax(int tourismTax) {
        this.tourismTax = tourismTax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public RoomReservation(int roomNo, String roomType, int days, String cost, int extraBed, Integer noOfPeople, String bedValue, String dateIn, String dateOut, int totalCharge, double serviceCharge, int tourismTax, double totalAmount){
        this.roomNo.setValue(roomNo);
        this.roomType.setValue(roomType);
        this.days.setValue(days);
        this.cost.setValue(cost);
        this.extraBed.setValue(extraBed);
        this.dateIn.setValue(dateIn);
        this.noOfPeople.setValue(noOfPeople);
        this.dateOut.setValue(dateOut);
        this.bedValue.setValue(bedValue);
        this.totalCharges.setValue(totalCharge);
        this.serviceCharge=serviceCharge;
        this.tourismTax=tourismTax;
        this.totalAmount=totalAmount;
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

    public String getRoomType() {
        return roomType.get();
    }

    public SimpleStringProperty roomTypeProperty() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public int getDays() {
        return days.get();
    }

    public SimpleIntegerProperty daysProperty() {
        return days;
    }

    public void setDays(int days) {
        this.days.set(days);
    }

    public String getCost() {
        return cost.get();
    }

    public SimpleStringProperty costProperty() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost.set(cost);
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

    public int getExtraBed() {
        return extraBed.get();
    }

    public SimpleIntegerProperty extraBedProperty() {
        return extraBed;
    }

    public void setExtraBed(int extraBed) {
        this.extraBed.set(extraBed);
    }

    public String getBedValue() {
        return bedValue.get();
    }

    public SimpleStringProperty bedValueProperty() {
        return bedValue;
    }

    public void setBedValue(String bedValue) {
        this.bedValue.set(bedValue);
    }

    public int getNoOfPeople() {
        return noOfPeople.get();
    }

    public SimpleIntegerProperty noOfPeopleProperty() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople.set(noOfPeople);
    }

    public int getTotalCharges() {
        return totalCharges.get();
    }

    public SimpleIntegerProperty totalChargesProperty() {
        return totalCharges;
    }

    public void setTotalCharges(int totalCharges) {
        this.totalCharges.set(totalCharges);
    }
}
