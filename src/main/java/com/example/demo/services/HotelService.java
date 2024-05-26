package com.example.demo.services;

import com.example.demo.Tuple;
import com.example.demo.entities.Hotel;
import com.example.demo.entities.Room;
import com.example.demo.repositories.HotelRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    private HotelRepository hotelRepository;
    private static final double EARTH_RADIUS = 6371.0; // in kilometers

    public HotelService() throws IOException {
        hotelRepository = new HotelRepository();
    }

    private static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = toRadians(lat1);
        lon1 = toRadians(lon1);
        lat2 = toRadians(lat2);
        lon2 = toRadians(lon2);

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public ArrayList<Tuple<Hotel, Double>> findClosestHotels(double userLat, double userLon, double radius) {
        ArrayList<Tuple<Hotel, Double>> hotelsInRadius = new ArrayList<>();

        for (Hotel hotel : hotelRepository.getAllHotels()) {
            double distance = calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude());
            if (distance <= radius) {
                Tuple<Hotel, Double> tuple = new Tuple<>(hotel, distance);
                hotelsInRadius.add(tuple);
            }
        }
        return hotelsInRadius;
    }

    public String getRoomType(int roomType) {
        if(roomType ==  1){
            return "Single room";
        }
        else if (roomType == 2){
            return "Double room";
        }
        else if (roomType == 3) {
            return "Suite room";
        } else if (roomType == 4) {
            return "Matrimonial room";
        }
        return "Unknown";
    }

    public void updateRoomAvailability(int hotelId, int roomNumber, boolean isAvailable) throws IOException {
        hotelRepository.updateRoomAvailability(hotelId, roomNumber, isAvailable);
    }

    public Hotel getHotelById(int hotel_id){
        return hotelRepository.getHotelById(hotel_id);
    }

    public Room getRoomByNumber(int hotel_id, int room_number){
        return hotelRepository.getRoomByNumber(hotel_id, room_number);
    }

    public List<Room> getAvailableRooms(int hotel_id) {
        List<Room> rooms = new ArrayList<>();
        Hotel hotel = hotelRepository.getHotelById(hotel_id);
        for (Room room: hotel.getRooms()) {
            if (room.getIsAvailable())
                rooms.add(room);
        }
        return rooms;
    }

}
