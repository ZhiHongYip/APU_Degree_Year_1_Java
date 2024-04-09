package com.example.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {
    public SimpleIntegerProperty rowNumber = new SimpleIntegerProperty();
    public SimpleIntegerProperty roomNo= new SimpleIntegerProperty();
    public SimpleStringProperty roomType= new SimpleStringProperty();
    public SimpleIntegerProperty cost = new SimpleIntegerProperty();
    public SimpleIntegerProperty maximumPeople= new SimpleIntegerProperty();
    public SimpleIntegerProperty maximumExtraBed= new SimpleIntegerProperty();
    public SimpleStringProperty availability= new SimpleStringProperty();
    public SimpleStringProperty dateIn= new SimpleStringProperty();
    public SimpleStringProperty dateOut= new SimpleStringProperty();
    public SimpleIntegerProperty extraBed= new SimpleIntegerProperty();
    public SimpleIntegerProperty noOfPeople= new SimpleIntegerProperty();
    public static String temp;

    public Room(){
    }

    public Room(int rowNumber, int roomNo, String roomType, int cost, int maximumPeople, int maximumExtraBed, String availability){
        this.rowNumber.set(rowNumber);
        this.roomNo.set(roomNo);
        this.roomType.set(roomType);
        this.cost.set(cost);
        this.maximumPeople.set(maximumPeople);
        this.maximumExtraBed.set(maximumExtraBed);
        this.availability.set(availability);
    }
    public int getRowNumber() {
        return rowNumber.get();
    }

    public SimpleIntegerProperty rowNumberProperty() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber.set(rowNumber);
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

    public int getCost() {
        return cost.get();
    }

    public SimpleIntegerProperty costProperty() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost.set(cost);
    }

    public int getMaximumPeople() {
        return maximumPeople.get();
    }

    public SimpleIntegerProperty maximumPeopleProperty() {
        return maximumPeople;
    }

    public void setMaximumPeople(int maximumPeople) {
        this.maximumPeople.set(maximumPeople);
    }

    public int getMaximumExtraBed() {
        return maximumExtraBed.get();
    }

    public SimpleIntegerProperty maximumExtraBedProperty() {
        return maximumExtraBed;
    }

    public void setMaximumExtraBed(int maximumExtraBed) {
        this.maximumExtraBed.set(maximumExtraBed);
    }

    public String getAvailability() {
        return availability.get();
    }

    public SimpleStringProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability.set(availability);
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

    public int getNoOfPeople() {
        return noOfPeople.get();
    }

    public SimpleIntegerProperty noOfPeopleProperty() {
        return noOfPeople;
    }

    public void setNoOfPeople(int noOfPeople) {
        this.noOfPeople.set(noOfPeople);
    }
}
