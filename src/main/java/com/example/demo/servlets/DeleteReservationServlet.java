package com.example.demo.servlets;

import com.example.demo.entities.Reservation;
import com.example.demo.services.HotelService;
import com.example.demo.services.ReservationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete_reservation")
public class DeleteReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int reservation_id = Integer.parseInt(request.getParameter("reservationId"));

        ReservationService reservationService = new ReservationService();
        Reservation res = reservationService.getReservationById(reservation_id);
        reservationService.deleteReservation(reservation_id);

        HotelService hotelService = new HotelService();
        hotelService.updateRoomAvailability(res.getHotelRoomTuple().getFirst(), res.getHotelRoomTuple().getSecond(),
                true);

        String cnp = request.getParameter("cnp");

        response.sendRedirect("reservations.jsp?cnp=" + cnp);
    }
}
