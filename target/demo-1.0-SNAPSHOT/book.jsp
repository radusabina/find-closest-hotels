<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking</title>
    <style>
        .form {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
        }

        .container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 300px;
            margin: 0;
        }

        label {
            margin-top: 10px;
            text-align: left;
            width: 100%;
            max-width: 300px;
        }

        input, select {
            margin-top: 5px;
            padding: 10px;
            width: 100%;
            max-width: 300px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            margin-top: 20px;
            background-color: blue;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: blue;
        }

        h2 {
            text-align: center;
            margin-bottom: 10px;
            margin-top: 0;
        }

        body {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<div class="form">
    <div class="container">
        <h2>Booking</h2>
        <form action="booking" method="post">

            <input type="hidden" name="latitude" value="<%= request.getParameter("latitude") %>">
            <input type="hidden" name="longitude" value="<%= request.getParameter("longitude") %>">
            <input type="hidden" name="radius" value="<%= request.getParameter("radius") %>">
            <input type="hidden" name="id" value="<%= request.getParameter("id") %>">
            <input type="hidden" name="hotelName" value="<%= request.getParameter("hotelName") %>">
            <input type="hidden" name="roomNumber" value="<%= request.getParameter("roomNumber") %>">

            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required><br>

            <label for="cnp">CNP:</label>
            <input type="text" id="cnp" name="cnp" required><br>

            <label for="paymentMethod">Payment Method:</label>
            <select id="paymentMethod" name="paymentMethod" required>
                <option value="creditCard">Credit Card</option>
                <option value="paypal">PayPal</option>
                <option value="cash">Cash</option>
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

                    for (int i = 0; i <= 8; i++) { %>
                <option value="<%= sdf.format(calendar.getTime()) %>"><%= sdf.format(calendar.getTime()) %></option>
                <%
                        calendar.add(Calendar.HOUR_OF_DAY, 1);
                    }
                %>
            </select><br>

            <input type="submit" value="Confirm Booking">
        </form>
    </div>
</div>
</body>
</html>
