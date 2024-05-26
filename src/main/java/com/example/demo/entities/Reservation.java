package com.example.demo.entities;

import com.example.demo.Tuple;

public class Reservation {
    private static int nextId = 1;
    private int id;
    private int id_user;
    private Tuple<Integer, Integer> hotelRoomTuple;
    private String paymentMethod;
    private String checkInTime;

    public Reservation() {}

    public Reservation(int id_user, Tuple<Integer, Integer> hotelRoomTuple, String paymentMethod, String checkInTime) {
        this.id = generateNextId();
        this.id_user = id_user;
        this.hotelRoomTuple = hotelRoomTuple;
        this.paymentMethod = paymentMethod;
        this.checkInTime = checkInTime;
    }

    public Reservation(int id_user, int hotelId, int roomNumber, String paymentMethod, String checkInTime) {
        this.id = generateNextId();
        this.id_user = id_user;
        this.hotelRoomTuple = new Tuple<>(hotelId, roomNumber);
        this.paymentMethod = paymentMethod;
        this.checkInTime = checkInTime;
    }

    private static int generateNextId() {
        return nextId++;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tuple<Integer, Integer> getHotelRoomTuple() { return hotelRoomTuple; }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setHotelRoomTuple(Tuple<Integer, Integer> hotel_room_tuple) {
        this.hotelRoomTuple = hotel_room_tuple;
    }

    public void setPaymentMethod(String payment_method) {
        this.paymentMethod = payment_method;
    }

    public void setCheckInTime(String check_in_time) {
        this.checkInTime = check_in_time;
    }

    public String getCheckInTime() {
        return checkInTime;
    }
}
