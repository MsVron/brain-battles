package com.quiz.controller;

import com.quiz.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class MainMenuController {

    @FXML
    private Button startQuizButton;
    
    @FXML
    private Button viewLeaderboardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Label label;

    // Méthode appelée lorsqu'on clique sur "Commencer un Quiz"
    @FXML
    private void handleStartQuiz(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DifficultySelection.fxml"));
            Scene difficultyScene = new Scene(loader.load());
            difficultyScene.getStylesheets().add(getClass().getResource("/styles/QuizView.css").toExternalForm());
            
            Stage stage = (Stage) startQuizButton.getScene().getWindow();
            DifficultySelectionController controller = loader.getController();
            controller.setStage(stage);
            controller.setUser(App.user); // Add this line
            
            stage.setScene(difficultyScene);
        } catch (Exception e) {
            showErrorAlert("Error", "Could not load difficulty selection.");
            e.printStackTrace();
        }
    }

    // Méthode appelée lorsqu'on clique sur "Voir le Classement"
    @FXML
    private void handleViewLeaderboard(ActionEvent event) {
        System.out.println("Voir le Classement");

        // Exemple de changement de scène pour afficher le classement
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LeaderboardView.fxml"));
            Parent leaderboardRoot = loader.load();

            Stage stage = new Stage();
            // Stage stage = (Stage) viewLeaderboardButton.getScene().getWindow();
            stage.setScene(new Scene(leaderboardRoot));
            stage.setTitle("Classement");
            stage.show();
        } catch (Exception e) {
            showErrorAlert("Erreur", "Impossible de charger le classement.");
            e.printStackTrace();
        }
    }

    // Méthode appelée lorsqu'on clique sur "Se Déconnecter"
    @FXML
    private void handleLogout(ActionEvent event) {
        System.out.println("Déconnexion");

        // Exemple de déconnexion (fermeture de l'application ou retour à l'écran de connexion)
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");
        alert.setContentText("Vous serez redirigé vers l'écran de connexion.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Fermer l'application ou rediriger vers l'écran de connexion
                Stage stage = (Stage) logoutButton.getScene().getWindow();
                
                // Vous pouvez également charger une scène de connexion ici
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
                    Parent loginRoot = loader.load();
                    Stage loginStage = new Stage();
                    loginStage.setScene(new Scene(loginRoot));
                    loginStage.setTitle("Connexion");
                    loginStage.show();

                    stage.close();
                } catch (Exception e) {
                    showErrorAlert("Erreur", "Impossible de charger l'écran de connexion.");
                    e.printStackTrace();
                }
            }
        });
    }

    // Méthode d'initialisation qui peut être utilisée pour personnaliser le label ou d'autres éléments
    @FXML
    private void initialize() {
        label.setText("Bienvenue dans l'application Quiz !");
    }

    // Méthode utilitaire pour afficher des alertes d'erreur
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
