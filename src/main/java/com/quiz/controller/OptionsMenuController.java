package com.quiz.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.quiz.App;
import javafx.scene.control.ButtonType;
import com.quiz.controller.QuizController;
import javafx.application.Platform;
import javafx.stage.Window;


public class OptionsMenuController {
    
    private Stage dialogStage;
    private QuizController quizController;
    
    public void setQuizController(QuizController controller) {
        this.quizController = controller;
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    @FXML
    private void handleReturn() {
        dialogStage.close();
        // The quiz will resume automatically in the handleSettings method if it was running before
    }
    
    @FXML
    private void handleNewGame() {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("New Game");
        confirmation.setHeaderText("Start New Game");
        confirmation.setContentText("Are you sure you want to start a new game? Your current progress will be lost.");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Close current quiz and options windows
                    quizController.closeQuizWindow();
                    dialogStage.close();
                    
                    // Load difficulty selection
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DifficultySelection.fxml"));
                    Scene difficultyScene = new Scene(loader.load());
                    
                    // Add CSS
                    String css = getClass().getResource("/styles/QuizView.css").toExternalForm();
                    difficultyScene.getStylesheets().add(css);
                    
                    // Create new stage and show difficulty selection
                    Stage stage = App.primaryStage;  // You'll need to make primaryStage public static in App class
                    
                    // Set up difficulty selection
                    DifficultySelectionController controller = loader.getController();
                    controller.setStage(stage);
                    
                    // Show difficulty selection
                    stage.setScene(difficultyScene);
                    stage.show();
                } catch (Exception e) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Error");
                    error.setContentText("Could not load difficulty selection.");
                    error.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }
 
    @FXML
    private void handleDisconnect() {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Déconnexion");
        confirmation.setHeaderText("Se déconnecter");
        confirmation.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.runLater(() -> {
                    try {
                        System.out.println("Starting disconnect process...");
                        
                        // First close this options dialog
                        if (dialogStage != null) {
                            dialogStage.close();
                        }
                        
                        // Then close quiz window if it exists
                        if (quizController != null) {
                            System.out.println("Closing quiz window...");
                            quizController.closeQuizWindow();
                        }
                        
                        // Small delay to ensure windows are closed
                        Platform.runLater(() -> {
                            System.out.println("Calling logout...");
                            App.logout();
                        });
                        
                    } catch (Exception e) {
                        System.err.println("Error during disconnect: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        });
    }
  
    @FXML
    private void handleQuit() {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Quitter");
        confirmation.setHeaderText("Quitter le quiz");
        confirmation.setContentText("Êtes-vous sûr de vouloir retourner au menu principal ?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    // Close current quiz and options windows
                    quizController.closeQuizWindow();
                    dialogStage.close();
                    
                    // Load main menu
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
                    Scene mainMenuScene = new Scene(loader.load());
                    
                    // Get the primary stage and set the main menu scene
                    Stage stage = App.primaryStage;
                    stage.setScene(mainMenuScene);
                    stage.show();
                    
                } catch (Exception e) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Error");
                    error.setContentText("Could not load main menu.");
                    error.showAndWait();
                    e.printStackTrace();
                }
            }
        });
    }
}