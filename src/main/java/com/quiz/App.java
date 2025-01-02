package com.quiz;


import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import com.quiz.database.entity.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quiz.database.DatabaseConnection;
import com.quiz.controller.QuizController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.stage.Window;


public class App {
    public static User user;
    public static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(JavaFXQuizApp.class, args);
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            
            // Reset window size to login screen dimensions
            primaryStage.setWidth(900);  // Set to your login screen's default width
            primaryStage.setHeight(600); // Set to your login screen's default height
            
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login screen: " + e.getMessage());
        }
    }
    
  public static void logout() {
    user = null; // Clear the current user
    
    Platform.runLater(() -> {
        try {
            // Store reference to primary stage
            Stage mainStage = primaryStage;
            if (mainStage == null) {
                System.err.println("Primary stage is null!");
                return;
            }
            
            // Create new login scene first
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/LoginView.fxml"));
            Scene loginScene = new Scene(loader.load());
            
            // Add CSS
            String css = App.class.getResource("/styles/LoginView.css").toExternalForm();
            loginScene.getStylesheets().clear();
            loginScene.getStylesheets().add(css);
            
            // Close all other windows
            for (Window window : new ArrayList<>(Window.getWindows())) {
                if (window instanceof Stage && window != mainStage) {
                    ((Stage) window).close();
                }
            }
            
            // Set up and show login screen on primary stage
            mainStage.setScene(loginScene);
            mainStage.setWidth(900);
            mainStage.setHeight(600);
            mainStage.centerOnScreen();
            mainStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load login screen: " + e.getMessage());
        }
    });
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
	           // Set the primary stage
	           App.setPrimaryStage(stage);
	           
	           // Set the application icon
	           try {
	               Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
	               stage.getIcons().add(icon);
	           } catch (Exception e) {
	               System.err.println("Failed to load application icon: " + e.getMessage());
	           }
	           
	           // Initialize d atabase connection
	           DatabaseConnection.getConnection();
	           System.out.println("Database connection established!");
	           
	           // Load FXML
	           URL fxmlPath = getClass().getResource("/fxml/LoginView.fxml");
	           FXMLLoader loader = new FXMLLoader(fxmlPath);
	           Scene scene = new Scene(loader.load());
	           
	           // Add CSS
	           String css = getClass().getResource("/styles/LoginView.css").toExternalForm();
	           scene.getStylesheets().add(css);
	           
	           stage.setTitle("Quiz Application");
	           stage.setScene(scene);
	           stage.show();
	           
	       } catch (SQLException e) {
	           e.printStackTrace();
	           System.err.println("Database connection failed: " + e.getMessage());
	       } catch (IOException e) {
	           e.printStackTrace();
	           System.err.println("Failed to load FXML: " + e.getMessage());
	       } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println("An unexpected error occurred: " + e.getMessage());
	       }
	   }
	}
}