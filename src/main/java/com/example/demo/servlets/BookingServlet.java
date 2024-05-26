package com.example.demo.servlets;

import com.example.demo.entities.User;
import com.example.demo.services.HotelService;
import com.example.demo.services.ReservationService;
import com.example.demo.services.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fullName = request.getParameter("fullName");
        String cnp = request.getParameter("cnp");

        UserService userService = new UserService();
        User existingUser = userService.getUserByCnp(cnp);

        if (userService.getUserByCnp(cnp) == null) {
            User newUser = new User(fullName, cnp);
            userService.addUser(newUser);
            existingUser = userService.getUserByCnp(cnp);
        }

        int hotelId = Integer.parseInt(request.getParameter("id"));
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        String paymentMethod = request.getParameter("paymentMethod");
        String checkInTime = request.getParameter("checkInTime");

        ReservationService reservationService = new ReservationService();
        reservationService.addReservation(existingUser.getId(), hotelId, roomNumber, paymentMethod, checkInTime);

        HotelService hotelService = new HotelService();
        hotelService.updateRoomAvailability(hotelId, roomNumber, false);

        double latitude = Double.parseDouble(request.getParameter("latitude"));
        double longitude = Double.parseDouble(request.getParameter("longitude"));
        double radius = Double.parseDouble(request.getParameter("radius"));


        response.sendRedirect("hotels.jsp?latitude=" + latitude + "&longitude=" + longitude + "&radius=" + radius);
    }
}
