package com.example.demo.repositories;

import com.example.demo.Tuple;
import com.example.demo.entities.Reservation;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private static final String FILE_PATH = "/Users/sabina/Desktop/demo/src/reservations.json";
    private ObjectMapper objectMapper;

    private List<Reservation> reservations;

    public ReservationRepository() {
        objectMapper = new ObjectMapper();
        reservations = getReservationsFromFile();
    }

    private int generateNextReservationId() {
        if (reservations.isEmpty()) {
            return 1;
        } else {
            return reservations.get(reservations.size() - 1).getId() + 1;
        }
    }

    private List<Reservation> getReservationsFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Reservation>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveReservations() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
        saveReservations();
    }

    public void addReservation(Reservation reservation) {
        reservation.setId(generateNextReservationId());
        reservations.add(reservation);
        saveReservations();
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public Reservation getReservationById(int id_reservation) {
        for (Reservation res: reservations){
            if (res.getId() == id_reservation)
                return res;
        }
        return null;
    }

    public void updateReservation(int id_reservation, int room_number, String payment_method, String check_in_time){
        for (Reservation res: reservations) {
            if (res.getId() == id_reservation) {
                res.setHotelRoomTuple(new Tuple<>(res.getHotelRoomTuple().getFirst(), room_number));
                res.setPaymentMethod(payment_method);
                res.setCheckInTime(check_in_time);
            }
        }
        saveReservations();
    }
}
