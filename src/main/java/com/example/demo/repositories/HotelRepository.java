package com.example.demo.repositories;

import com.example.demo.entities.Hotel;
import com.example.demo.entities.Room;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HotelRepository {
    private List<Hotel> hotels;

    public HotelRepository() throws IOException {
        hotels = readHotelsFromFile();
    }

    public List<Hotel> getAllHotels() {
        return hotels;
    }

    private List<Hotel> readHotelsFromFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("/Users/sabina/Desktop/demo/src/hotels.json");
        return objectMapper.readValue(file, new TypeReference<List<Hotel>>() {});
    }

    public void updateRoomAvailability(int hotelId, int roomNumber, boolean isAvailable) throws IOException {
        for (Hotel hotel : hotels) {
            if (hotel.getId() == hotelId) {
                for (Room room : hotel.getRooms()) {
                    if (room.getRoomNumber() == roomNumber) {
                        room.setIsAvailable(isAvailable);
                        saveHotelsToFile();
                        return;
                    }
                }
            }
        }
    }

    private void saveHotelsToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("/Users/sabina/Desktop/demo/src/hotels.json"), hotels);
    }

    public Hotel getHotelById(int hoted_id) {
        for (Hotel hotel: hotels) {
            if (hotel.getId() == hoted_id)
                return hotel;
        }
        return null;
    }

    public Room getRoomByNumber(int hoted_id, int room_number) {
        Hotel hotel = getHotelById(hoted_id);
        for (Room room: hotel.getRooms()) {
            if (room.getRoomNumber() == room_number)
                return room;
        }
        return  null;
    }
}
