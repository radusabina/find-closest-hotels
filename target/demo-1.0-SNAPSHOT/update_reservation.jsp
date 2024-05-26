<%@ page import="com.example.demo.entities.Reservation" %>
<%@ page import="com.example.demo.services.ReservationService" %>
<%@ page import="com.example.demo.services.HotelService" %>
<%@ page import="com.example.demo.entities.Hotel" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entities.Room" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %><%--
  Created by IntelliJ IDEA.
  User: sabina
  Date: 17.05.2024
  Time: 13:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update reservation</title>
    <script type="text/javascript">
        function updateRoomDetails() {
            var roomSelect = document.getElementById("roomNumber");
            var selectedOption = roomSelect.options[roomSelect.selectedIndex];
            var roomType = selectedOption.getAttribute("data-room-type");
            var roomPrice = selectedOption.getAttribute("data-room-price");
            document.getElementById("roomType").innerText = "Room type: " + roomType;
            document.getElementById("roomPrice").innerText = "Room price: $" + roomPrice;
        }
    </script>

    <style>
        .container {
            width: 400px;
            margin: 150px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 10px;
        }

        h3 {
            text-align: center;
        }

        select, input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: blue;
            color: white;
            cursor: pointer;
            margin-top: 20px;
        }

        input[type="submit"]:hover {
            background-color: darkblue;
        }
    </style>
</head>
<body>

<%
    ReservationService reservationService = new ReservationService();
    Reservation res = reservationService.getReservationById(Integer.parseInt(request.getParameter("reservationId")));

    HotelService hotelService = new HotelService();
    Hotel hotel = hotelService.getHotelById(res.getHotelRoomTuple().getFirst());

    List<Room> availableRooms = hotelService.getAvailableRooms(hotel.getId());

    Room currentRoom = hotelService.getRoomByNumber(hotel.getId(), res.getHotelRoomTuple().getSecond());
    String currentRoomType = hotelService.getRoomType(currentRoom.getType());
    double currentRoomPrice = currentRoom.getPrice();
%>

<div class="container">
<h3>Hotel <%= hotel.getName() %></h3>

<form action="update_reservation" method="post" style="display: inline;">
    <input type="hidden" name="reservationId" value="<%= res.getId() %>">
    <input type="hidden" name="cnp" value="<%= request.getParameter("cnp") %>">

    <label for="roomNumber">Room Number:</label>
    <select id="roomNumber" name="roomNumber" required onchange="updateRoomDetails()">
        <option value="<%= currentRoom.getRoomNumber() %>" data-room-type="<%= currentRoomType %>" data-room-price="<%= currentRoomPrice %>" selected>
            <%= currentRoom.getRoomNumber() %>
        </option>
        <% for (Room room : availableRooms) { %>
        <% if (room.getRoomNumber() != currentRoom.getRoomNumber()) { %>
        <option value="<%= room.getRoomNumber() %>" data-room-type="<%= hotelService.getRoomType(room.getType()) %>" data-room-price="<%= room.getPrice() %>">
            <%= room.getRoomNumber() %>
        </option>
        <% } %>
        <% } %>
    </select><br>

    <p id="roomType">Room type: <%= currentRoomType %></p>
    <p id="roomPrice">Room price: $<%= currentRoomPrice %></p>

    <label for="paymentMethod">Payment Method:</label>
    <select id="paymentMethod" name="paymentMethod" required>
        <option value="creditCard" <%= "creditCard".equals(res.getPaymentMethod()) ? "selected" : "" %>>Credit Card</option>
        <option value="paypal" <%= "paypal".equals(res.getPaymentMethod()) ? "selected" : "" %>>PayPal</option>
        <option value="cash" <%= "cash".equals(res.getPaymentMethod()) ? "selected" : "" %>>Cash</option>
    </select><br>

    <label for="checkInTime">Check-in Time:</label>
    <select id="checkInTime" name="checkInTime" required>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar calendar = Calendar.getInstance();

            if (calendar.get(Calendar.MINUTE) > 0 || calendar.get(Calendar.SECOND) > 0) {
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }

            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            String defaultCheckInTime = res.getCheckInTime(); // Obțineți timpul de check-in din obiectul Reservation

            for (int i = 1; i <= 8; i++) {
                String currentTime = sdf.format(calendar.getTime());
        %>
        <option value="<%= currentTime %>" <%= currentTime.equals(defaultCheckInTime) ? "selected" : "" %>><%= currentTime %></option>
        <%
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }
        %>
    </select><br>

    <p>* If you change the room, you will have to pay for the price difference or the price difference will be sent back to you</p>

    <input type="submit" value="Save changes" class="reservation_button">

    </form>
</div>

</body>
</html>
