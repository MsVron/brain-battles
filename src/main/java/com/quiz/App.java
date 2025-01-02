package com.quiz;

import java.io.IOException;
import java.net.URL;
import com.quiz.database.entity.User;
import java.sql.SQLException;

import com.quiz.database.DatabaseConnection;
import com.quiz.controller.QuizController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {
    public static User user;
    private static Stage primaryStage;

    public static void main(String[] args) {
        JavaFXQuizApp.main(args);
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logout() {
        user = null; // Clear the current user
        showLoginScreen(); // Show the login screen
    }

   public static void loadQuiz() {
    try {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/QuizView.fxml")); // Changed from quiz-view.fxml to QuizView.fxml
        Scene scene = new Scene(loader.load());
        
        QuizController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        controller.resetQuiz(); // Reset the quiz state
        
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Failed to load QuizView.fxml: " + e.getMessage());
    }
}
    public static class JavaFXQuizApp extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            System.out.println("APP RUNNING...");
            
            try {
                // Initialize database connection
                DatabaseConnection.getConnection();
                System.out.println("Database connection established!");
                
                // Set the primary stage reference
                App.setPrimaryStage(stage);
                
                // Load initial login screen
                URL fxmlPath = getClass().getResource("/fxml/LoginView.fxml");
                FXMLLoader loader = new FXMLLoader(fxmlPath);
                
                stage.setTitle("Quiz Application");
                stage.setScene(new Scene(loader.load()));
                stage.show();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
}