<!DOCTYPE html>
<html>
<head>
  <title>Location</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    #container {
      text-align: center;
    }

    #radius, #cnp {
      margin-bottom: 20px;
      padding: 7px;
      border-radius: 10px;
      width: 150px;
    }

    #getLocationLink {
      background-color: blue;
      color: white;
      padding: 10px 20px;
      border-radius: 5px;
      cursor: pointer;
      text-decoration: none;
    }

    #getLocationLink:hover {
      background-color: darkblue;
    }

    #reservationsLink {
      margin-top: 20px;
      display: inline-block;
      text-decoration: none;
      color: blue;
      font-weight: bold;
      cursor: pointer;
    }

    #reservationsLink:hover {
      text-decoration: underline;
    }

    #cnpInput {
      display: none;
      margin-top: 20px;
    }

    #submitCNP {
      background-color: blue;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin-top: 10px;
    }

    #submitCNP:hover {
      background-color: darkblue;
    }
  </style>
  <script>
    $(document).ready(function () {
      $('#getLocationLink').click(function(event) {
        event.preventDefault();
        getLocation();
      });

      $('#reservationsLink').click(function(event) {
        event.preventDefault();
        $('#cnpInput').show();
      });

      $('#submitCNP').click(function() {
        const cnp = $('#cnp').val();
        if (cnp !== "") {
          const url = "reservations.jsp?cnp=" + cnp;
          window.location.href = url;
        } else {
          console.log("Please enter a valid CNP.");
        }
      });

      function getLocation() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(showPosition);
        } else {
          console.log("Geolocation is not supported by this browser.");
        }
      }

      function showPosition(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        $('#latitude').text("Latitude: " + latitude);
        $('#longitude').text("Longitude: " + longitude);

        const radius = $('#radius').val();
        if (radius !== "") {
          const url = "hotels.jsp?latitude=" + latitude + "&longitude=" + longitude + "&radius=" + radius;
          window.location.href = url;
        } else {
          console.log("Please enter a valid radius.");
        }
      }
    });
  </script>
</head>
<body>
<div id="container">
  <h1>Find hotels near you</h1>
  <label for="radius">Radius (km): </label>
  <input type="text" id="radius" name="radius">
  <br />
  <a id="getLocationLink" href="#">Get My Location</a>
  <br />
  <a id="reservationsLink" href="#">See your reservations</a>
  <div id="cnpInput">
    <label for="cnp">Enter your CNP: </label>
    <input type="text" id="cnp" name="cnp">
    <br />
    <button id="submitCNP">Submit</button>
  </div>
</div>
</body>
</html>
