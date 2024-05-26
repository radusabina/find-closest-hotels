<%@ page import="java.time.LocalTime" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.example.demo.services.UserService" %>
<%@ page import="com.example.demo.entities.User" %>
<%@ page import="com.example.demo.entities.Reservation" %>
<%@ page import="com.example.demo.services.ReservationService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.services.HotelService" %>
<%@ page import="com.example.demo.entities.Hotel" %>
<%@ page import="com.example.demo.entities.Room" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reservations</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
        }

        #container {
            text-align: center;
            width: 80%;
        }

        ul {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            list-style-type: none;
            padding: 0;
            margin: 0;
        }

        li {
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 20px;
            background-color: #f9f9f9;
        }

        .reservation_button {
            background-color: #454444;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        #feedback {
            width: 80%;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div id="container">
    <%
        String cnp = request.getParameter("cnp");
        User user = null;

        if (cnp != null && !cnp.trim().isEmpty()) {
            try {
                UserService userService = new UserService();
                user = userService.getUserByCnp(cnp);
            } catch (Exception e) {
                System.err.println("Error retrieving user: " + e.getMessage());
            }
        }

        if (user != null) {
            try {
                ReservationService reservationService = new ReservationService();
                List<Reservation> userReservations = reservationService.getUserReservations(user.getId());

                HotelService hotelService = new HotelService();

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                LocalTime twoHoursAheadStr = LocalTime.now().plusHours(2);
    %>
    <p>Welcome, <%= user.getFullName() %>!</p>

    <% if (userReservations != null && !userReservations.isEmpty()) { %>
    <h2>Your Reservations:</h2>
    <ul>
        <%
            for (Reservation reservation : userReservations) {
                try {
                    Hotel hotel = hotelService.getHotelById(reservation.getHotelRoomTuple().getFirst());
                    Room room = hotelService.getRoomByNumber(reservation.getHotelRoomTuple().getFirst(),
                            reservation.getHotelRoomTuple().getSecond());

                    LocalTime checkInTime = LocalTime.parse(reservation.getCheckInTime());

                    long diffHours, diffMinutes;
                    if (twoHoursAheadStr.isBefore(checkInTime)){
                        diffHours = Math.abs(checkInTime.getHour() - twoHoursAheadStr.getHour());
                        diffMinutes = Math.abs(checkInTime.getMinute() - twoHoursAheadStr.getMinute());
                    }
                    else {
                        diffHours = 0;
                        diffMinutes = 0;
                    }

        %>
        <li>
            <p>Hotel Name: <%= hotel.getName() %></p>
            <p>Room Number: <%= reservation.getHotelRoomTuple().getSecond() %></p>
            <p>Price: <%= room.getPrice() %></p>
            <p>Payment Method: <%= reservation.getPaymentMethod() %></p>
            <p>Check-in Time: <%= reservation.getCheckInTime() %></p>

            <%
                if (diffHours > 0 || diffMinutes > 0) {
            %>
            <form action="delete_reservation" method="post" style="display: inline;">
                <input type="hidden" name="reservationId" value="<%= reservation.getId() %>">
                <input type="hidden" name="cnp" value="<%= request.getParameter("cnp") %>">
                <input type="submit" value="Delete Reservation" class="reservation_button">
            </form>
            <form action="update_reservation.jsp" method="post" style="display: inline;">
                <input type="hidden" name="reservationId" value="<%= reservation.getId() %>">
                <input type="hidden" name="cnp" value="<%= request.getParameter("cnp") %>">
                <input type="submit" value="Update Reservation" class="reservation_button">
            </form>
            <%
                } else {
                    if (LocalTime.now().isAfter(checkInTime)){
            %>
            <div>
                <form action="submit_feedback" method="post">
                    <input type="hidden" name="cnp" value="<%= request.getParameter("cnp") %>">
                    <input type="hidden" name="hotelId" value="<%= reservation.getHotelRoomTuple().getFirst() %>">
                    <input type="hidden" name="roomNumber" value="<%= reservation.getHotelRoomTuple().getSecond() %>">
                    <label for="feedback"></label>
                    <textarea id="feedback" name="feedback" rows="3" cols="50" placeholder="Your feedback..."></textarea><br>
                <input type="submit" value="Submit Feedback" class="reservation_button">
            </form>
            </div>
            <% }}%>
        </li>
        <%
                } catch (Exception e) {
                    System.err.println("Error retrieving reservation details: " + e.getMessage());
                }
            }
        %>
    </ul>
    <%  } else { %>
    <p>You don't have any reservations.</p>
    <%  } %>

    <%      } catch (Exception e) { %>
    <p>Error retrieving reservations: <%= e.getMessage() %></p>
    <%      } %>
    <%  } else { %>
    <p>You don't have any reservations.</p>
    <%  } %>
</div>
</body>
</html>
