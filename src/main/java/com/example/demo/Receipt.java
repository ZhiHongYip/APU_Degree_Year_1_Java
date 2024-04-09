package com.example.demo;

public class Receipt {
    public String customerName;
    public String phoneNumber;
    public String emailAddress;
    public String paymentMethod;

    public String roomNo;
    public String extraBed;
    public String checkInDate;
    public String checkOutDate;
    public String people;
    public String days;
    public String service_tax;
    public String tourismTax;
    public String total;
    public String totalAmount;

    public Receipt(String customerName, String phoneNumber, String emailAddress, String paymentMethod, String roomNo, String extraBed,String CheckInDate, String CheckOutDate, String people, String days,String total,String service_tax, String tourismTax, String totalAmount){
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.paymentMethod = paymentMethod;
        this.roomNo = roomNo;
        this.extraBed = extraBed;
        this.checkInDate = CheckInDate;
        this.checkOutDate =CheckOutDate;
        this.people = people;
        this.days = days;
        this.total = total;
        this.service_tax = service_tax;
        this.tourismTax = tourismTax;
        this.totalAmount =totalAmount;

    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getExtraBed() {
        return extraBed;
    }

    public void setExtraBed(String extraBed) {
        this.extraBed = extraBed;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
