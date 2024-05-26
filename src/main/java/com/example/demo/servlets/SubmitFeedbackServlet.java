package com.example.demo.servlets;

import com.example.demo.services.FeedbackService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/submit_feedback")
public class SubmitFeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int hotel_id = Integer.parseInt(request.getParameter("hotelId"));
        int room_number = Integer.parseInt(request.getParameter("roomNumber"));
        String feedback = request.getParameter("feedback");

        FeedbackService feedbackService = new FeedbackService();
        feedbackService.addFeedback(hotel_id, room_number, feedback);

        String cnp = request.getParameter("cnp");

        response.sendRedirect("reservations.jsp?cnp=" + cnp);
    }
}
