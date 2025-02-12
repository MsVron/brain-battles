package com.quiz.controller;

import javafx.fxml.FXML;
import com.quiz.App;
import com.quiz.database.entity.User;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DifficultySelectionController {
	
	private User user;

    private Stage stage;
    
    public void setUser(User user) {
        this.user = user;
    }

    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void selectEasy() {
        loadQuiz(30); // 30 seconds per question
    }

    @FXML
    private void selectMedium() {
        loadQuiz(20); // 20 seconds per question
    }

    @FXML
    private void selectHard() {
        loadQuiz(10); // 10 seconds per question
    }

    private void loadQuiz(int timePerQuestion) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/QuizView.fxml"));
            Scene quizScene = new Scene(loader.load());
            
            QuizController quizController = loader.getController();
            quizController.setTimePerQuestion(timePerQuestion);
            quizController.setPrimaryStage(stage);
            quizController.setUser(this.user);
            quizController.resetQuiz();
            
            stage.setScene(quizScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}