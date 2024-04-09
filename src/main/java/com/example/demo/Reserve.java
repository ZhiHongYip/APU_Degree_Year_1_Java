package com.example.demo;

import java.time.LocalDate;

public class Reserve {
    private String username;
    private String phone_number_1;
    private String phone_number_2;
    private String ic_number;
    private String email_address;
    public static int customer_index = 8;
    private static Integer personPerRoom;
    private int roomNo;
    private static Integer extraBed;
    private LocalDate reservedTime;
    private String dateIn;
    private String dateOut;
    public static Reserve customerList = new Reserve();

    public Reserve(){

    }

    public Reserve(String username, String phone_number_1, String phone_number_2, String ic_number, String email_address){
        this.username = username;
        this.phone_number_1 = phone_number_1;
        this.phone_number_2 = phone_number_2;
        this.ic_number = ic_number;
        this.email_address = email_address;
    }

    public Reserve(Integer personPerRoom, Integer extraBed){
        Reserve.personPerRoom = personPerRoom;
        Reserve.extraBed = extraBed;
    }

    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}
    public String getPhone_number_1(){return this.phone_number_1;}
    public void setPhone_number_1(String phone_number_1){this.phone_number_1 = phone_number_1;}

    public String getPhone_number_2(){return this.phone_number_2;}
    public void setPhone_number_2(String phone_number_2){this.phone_number_2 = phone_number_2;}

    public LocalDate getReservedTime(){return this.reservedTime;}
    public void setReservedTime(LocalDate reservedTime){this.reservedTime = reservedTime;}

    public String getDateIn(){return this.dateIn;}
    public void setDateIn(String dateIn){this.dateIn = dateIn;}
    public String getDateOut(){return this.dateOut;}
    public void setDateOut(String dateOut){this.dateOut = dateOut;}
    public static void setExtraBed(Integer ExtraBed){ extraBed = ExtraBed;}
    public static Integer getPersonPerRoom(){return personPerRoom;}
    public static void setPersonPerRoom(Integer PersonPerRoom){personPerRoom = PersonPerRoom; }
    public String getIc_number(){
        return this.ic_number;
    }

    public void setIc_number(String ic_number) {
        this.ic_number = ic_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public int getRoomNo(){return this.roomNo;}

    public void setRoomNo(int roomNo){this.roomNo = roomNo;}

}
