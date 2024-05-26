<%@ page import="com.example.demo.services.HotelService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.demo.Tuple" %>
<%@ page import="com.example.demo.entities.Hotel" %>
<%@ page import="com.example.demo.entities.Room" %>
<%@ page import="com.example.demo.services.FeedbackService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.demo.entities.Feedback" %>
<!DOCTYPE html>
<html>
<head>
    <title>Closest Hotels</title>
    <style>
        h2 {
            text-align: center;
        }

        .hotels {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 25px;
            height: 100vh;
            margin: 0;
        }

        .hotel-container {
            border: 1px solid #ccc;
            margin-bottom: 20px;
            padding: 10px;
            border-radius: 5px;
            width: 350px;
            box-sizing: border-box;
        }

        .room-container {
            border: 1px solid #eee;
            margin-top: 10px;
            padding: 10px;
            border-radius: 5px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            min-height: 120px;
        }

        .available {
            color: green;
        }

        .not-available {
            color: red;
        }

        #hotel_name {
            margin-bottom: 5px;
        }

        #rooms_header {
            margin-top: 0px;
            margin-bottom: 5px;
        }

        #feedback {
            margin-top: 0;
            margin-bottom: 0;
            margin-left: 5px;
        }
    </style>


</head>
<body>
<%
    HotelService hotelService = new HotelService();

    double latitude = Double.parseDouble(request.getParameter("latitude"));
    double longitude = Double.parseDouble(request.getParameter("longitude"));
    double radius = Double.parseDouble(request.getParameter("radius"));

    ArrayList<Tuple<Hotel, Double>> closestHotels = hotelService.findClosestHotels(latitude, longitude, radius);
    FeedbackService feedbackService = new FeedbackService();
%>

<h2>Closest hotels to you</h2>
<br />
<div class="hotels">
    <% for (Tuple<Hotel, Double> tuple: closestHotels) { %>
    <div class="hotel-container">

        <h3 id="hotel_name"><%= tuple.getFirst().getName() %> --> <%= String.format("%.3f", tuple.getSecond()) %> km</h3>
        <h4 id="rooms_header">Rooms:</h4>
        <div>
            <% for (Room room : tuple.getFirst().getRooms()) { %>
            <div class="room-container">

                <p>Room Number: <%= room.getRoomNumber() %></p>
                <p>Type: <%= hotelService.getRoomType(room.getType()) %></p>
                <p>Price: <%= room.getPrice() %></p>
                <% if (room.getIsAvailable()) { %>
                <form action="book.jsp" method="post">

                    <input type="hidden" name="latitude" value="<%= latitude %>">
                    <input type="hidden" name="longitude" value="<%= longitude %>">
                    <input type="hidden" name="radius" value="<%= radius %>">
                    <input type="hidden" name="id" value="<%= tuple.getFirst().getId() %>">
                    <input type="hidden" name="hotelName" value="<%= tuple.getFirst().getName() %>">
                    <input type="hidden" name="roomNumber" value="<%= room.getRoomNumber() %>">
                    <input type="hidden" name="price" value="<%= room.getPrice() %>">

                    <button type="submit" class="available">Book</button>
                </form>
                <% } else { %>
                <p class="not-available">Not available</p>
                <% }
                    List<Feedback> feedbacks = feedbackService.getFeedbackByHotelAndRoom(tuple.getFirst().getId(),
                            room.getRoomNumber());
                    if(!feedbacks.isEmpty())
                    {
                        %>
                <p style="margin-bottom: 0;"> Feedbacks: </p>
                <%
                        for (Feedback feedback: feedbacks) {
                %>
                <p id="feedback"><%= feedback.getFeedback() %></p>
                <%
                        }}
                %>
            </div>
            <% } %>
        </div>
    </div>
    <% } %>
</div>

</body>
</html>
