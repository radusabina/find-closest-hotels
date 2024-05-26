package com.example.demo.services;

import com.example.demo.entities.Feedback;
import com.example.demo.repositories.FeedbackRepository;

import java.util.ArrayList;
import java.util.List;

public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService(){
        feedbackRepository = new FeedbackRepository();
    }

    public void addFeedback(int id_hotel, int room_number, String message){
        Feedback feedback = new Feedback(id_hotel, room_number, message);
        feedbackRepository.addFeedback(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.getFeedbacks();
    }

    public List<Feedback> getFeedbackByHotelAndRoom(int id_hotel, int room_number){
        List<Feedback> feedbacks = new ArrayList<>();
        for (Feedback feedback: feedbackRepository.getFeedbacks()){
            if (feedback.getId_hotel() == id_hotel && feedback.getRoom_number() == room_number)
                feedbacks.add(feedback);
        }
        return feedbacks;
    }
}
