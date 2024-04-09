package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {
    @FXML
    private Label checkInDate;

    @FXML
    private Label checkOutDate;

    @FXML
    private Label days;

    @FXML
    private Label emailAddress;

    @FXML
    private Label extraBed;

    @FXML
    private Label payment;

    @FXML
    private Label people;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label roomNo;
    @FXML
    private Label total;
    @FXML
    private Label totalAmount;
    @FXML
    private Label service_tax;
    @FXML
    private Label tourismTax;

    @FXML
    private Label userName;

    @FXML
    public static ObservableList<Receipt> receiptData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = receiptData.size();
        System.out.println("size" +i);
        int l = i-1;
        System.out.println(receiptData.get(l).paymentMethod);
        this.userName.setText(receiptData.get(l).customerName);
        this.phoneNumber.setText(receiptData.get(l).phoneNumber);
        this.emailAddress.setText(receiptData.get(l).emailAddress);
        this.checkInDate.setText(receiptData.get(l).checkInDate);
        this.checkOutDate.setText(receiptData.get(l).checkOutDate);
        this.payment.setText(receiptData.get(l).paymentMethod);
        this.roomNo.setText(receiptData.get(l).roomNo);
        this.extraBed.setText(receiptData.get(l).extraBed);
        this.people.setText(receiptData.get(l).people);
        this.days.setText(receiptData.get(l).days);
        this.total.setText(receiptData.get(l).total);
        this.service_tax.setText(receiptData.get(l).service_tax);
        this.tourismTax.setText(receiptData.get(l).tourismTax);
        this.totalAmount.setText(receiptData.get(l).totalAmount);
    }
}
