package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Room {
    private int roomNumber;
    private int type;
    private double price;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

}
