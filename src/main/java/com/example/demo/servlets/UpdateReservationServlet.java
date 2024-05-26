package com.example.demo.servlets;

import com.example.demo.entities.Hotel;
import com.example.demo.entities.Reservation;
import com.example.demo.services.HotelService;
import com.example.demo.services.ReservationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/update_reservation")
public class UpdateReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int reservation_id = Integer.parseInt(request.getParameter("reservationId"));
        String cnp = request.getParameter("cnp");
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        String paymentMethod = request.getParameter("paymentMethod");
        String checkInTime = request.getParameter("checkInTime");


        ReservationService reservationService = new ReservationService();
        HotelService hotelService = new HotelService();

        Reservation res = reservationService.getReservationById(reservation_id);
        Hotel hotel = hotelService.getHotelById(res.getHotelRoomTuple().getFirst());

        hotelService.updateRoomAvailability(hotel.getId(), res.getHotelRoomTuple().getSecond(), true);
        reservationService.updateReservation(reservation_id, roomNumber, paymentMethod, checkInTime);
        hotelService.updateRoomAvailability(hotel.getId(), roomNumber, false);

        response.sendRedirect("reservations.jsp?cnp=" + cnp);
    }
}
