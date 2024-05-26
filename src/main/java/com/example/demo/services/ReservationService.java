package com.example.demo.services;

import com.example.demo.entities.Reservation;
import com.example.demo.repositories.ReservationRepository;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationService() {
        this.reservationRepository = new ReservationRepository();
    }

    public void addReservation(int user_id, int hotelId, int roomNumber, String paymentMethod, String checkInTime) {
        Reservation reservation = new Reservation(user_id, hotelId, roomNumber, paymentMethod, checkInTime);
        reservationRepository.addReservation(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    public List<Reservation> getUserReservations(int user_id) {
        List<Reservation> user_reservations = new ArrayList<>();
        for (Reservation res: reservationRepository.getAllReservations()){
            if (res.getId_user() == user_id) {
                user_reservations.add(res);
            }
        }
        return user_reservations;
    }

    public Reservation getReservationById(int id_reservation) {
        return reservationRepository.getReservationById(id_reservation);
    }

    public void deleteReservation(int id_reservation) {
        Reservation res = reservationRepository.getReservationById(id_reservation);
        if (res != null)
            reservationRepository.deleteReservation(res);
    }

    public void updateReservation(int id_reservation, int room_number, String payment_method, String check_in_time) {
        reservationRepository.updateReservation(id_reservation, room_number, payment_method, check_in_time);
    }
}

