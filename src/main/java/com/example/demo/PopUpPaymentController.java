package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class PopUpPaymentController implements Initializable {
    @FXML
    private Button cashBtn;
    @FXML
    private Button cardBtn;
    @FXML
    private Button eWalletBtn;
    @FXML
    private Button onlineBankingBtn;

    String paymentMethod = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    @FXML
    public void card(ActionEvent event)throws IOException {
        paymentMethod = "Credit/ Debit Card";
        updatePaymentMethod();
        loadReceipt();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("receipt.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String imagePath = "hotel logo transparent.png";
        Image icon = new Image(imagePath);
        stage.getIcons().add(icon);
        stage.setTitle("Receipt");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    public void cash(ActionEvent event)throws IOException {
        paymentMethod = "Cash";
        updatePaymentMethod();
        loadReceipt();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("receipt.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String imagePath = "hotel logo transparent.png";
        Image icon = new Image(imagePath);
        stage.getIcons().add(icon);
        stage.setTitle("Receipt");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    public void eWallet(ActionEvent event)throws IOException {
        paymentMethod = "E-Wallet";
        updatePaymentMethod();
        loadReceipt();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("receipt.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String imagePath = "hotel logo transparent.png";
        Image icon = new Image(imagePath);
        stage.getIcons().add(icon);
        stage.setTitle("Receipt");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    public void onlineBanking(ActionEvent event)throws IOException {
        paymentMethod = "Online Banking";
        updatePaymentMethod();
        loadReceipt();
        Parent reserve = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("receipt.fxml")));
        Scene scene = new Scene(reserve);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String imagePath = "hotel logo transparent.png";
        Image icon = new Image(imagePath);
        stage.getIcons().add(icon);
        stage.setTitle("Receipt");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }


    public void updatePaymentMethod()throws IOException{
        String line = null;
        ArrayList<String> oldContent = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/demo/TextFiles/Receipt.txt"));
        while ((line = reader.readLine()) != null ){
            String [] info = line.split(",");

            if (Integer.parseInt(info[1])== CustomerListController.roomNO && info[3].equals(CustomerListController.dateIN) && info[4].equals(CustomerListController.dateOUT)){
                System.out.println("PopUpPaymentController"+ Arrays.toString(info));
                info[11]=paymentMethod;

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
            sb.append(info[8]).append(",");
            sb.append(info[9]).append(",");
            sb.append(info[10]).append(",");
            sb.append(info[11]);

            oldContent.add(String.valueOf(sb));
            System.out.println("oldContent: "+oldContent);
        }


        File file3 = new File("src/main/resources/com/example/demo/TextFiles/Receipt.txt");
        FileWriter writer3 = new FileWriter(file3);
        BufferedWriter br3 = new BufferedWriter(writer3);
        for (String s : oldContent) {
            br3.write(s);
            br3.newLine();
        }
        br3.close();
    }

    public void loadReceipt()throws IOException{
        String line = null;
        String line2 = null;
        String customerName = null;
        String phoneNumber= null;
        String emailAddress= null;
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
            }
        }
    }
}
