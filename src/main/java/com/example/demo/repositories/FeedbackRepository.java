package com.example.demo.repositories;

import com.example.demo.entities.Feedback;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepository {
    private static final String FILE_PATH = "/Users/sabina/Desktop/demo/src/feedbacks.json";
    private final ObjectMapper objectMapper;
    private List<Feedback> feedbacks;

    public FeedbackRepository(){
        objectMapper = new ObjectMapper();
        feedbacks = getFeedbacksFromFile();
    }

    private int generateNextId() {
        if (feedbacks.isEmpty()) {
            return 1;
        } else {
            return feedbacks.get(feedbacks.size() - 1).getId() + 1;
        }
    }

    private List<Feedback> getFeedbacksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Feedback>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void saveFeedbacks() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), feedbacks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Feedback> getFeedbacks(){
        return feedbacks;
    }

    public void addFeedback(Feedback feedback) {
        feedback.setId(generateNextId());
        feedbacks.add(feedback);
        saveFeedbacks();
    }
}
