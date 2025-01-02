package com.quiz.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import com.quiz.App;
import javafx.scene.control.ButtonType;
import com.quiz.controller.QuizController;

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
                // The quiz is already paused from opening settings
                quizController.closeQuizWindow();
                dialogStage.close();
                App.loadQuiz();
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
                // Close options dialog first
                dialogStage.close();
                
                // Close the quiz window
                quizController.closeQuizWindow();
                
                // Logout (this will show the login screen)
                App.logout();
            }
        });
    }
    
    @FXML
    private void handleQuit() {
        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Quitter");
        confirmation.setHeaderText("Quitter l'application");
        confirmation.setContentText("Êtes-vous sûr de vouloir quitter ?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}