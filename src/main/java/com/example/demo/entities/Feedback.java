package com.example.demo.entities;

public class Feedback {

    private int id;
    private int id_hotel;
    private int room_number;
    private String feedback;

    public Feedback(){}

    public Feedback(int id_hotel, int room_number, String feedback){
        this.id_hotel = id_hotel;
        this.room_number= room_number;
        this.feedback =feedback;
    }

    public int getId() {
        return id;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public int getRoom_number() {
        return room_number;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
