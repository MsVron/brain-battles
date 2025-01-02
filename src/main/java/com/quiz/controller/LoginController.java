package com.quiz.controller;

import com.quiz.database.dao.UserDao;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.application.Platform;

import com.quiz.App;
import com.quiz.database.entity.User;


public class LoginController {
	
@FXML
public void initialize() {
    // Wait for the scene to be set before trying to access the window/stage
    Platform.runLater(() -> {
        if (emailField != null && emailField.getScene() != null) {
            Stage stage = (Stage) emailField.getScene().getWindow();
            if (stage != null) {
                stage.setWidth(900);
                stage.setHeight(600);
                stage.centerOnScreen();
                
                // Load CSS
                Scene scene = emailField.getScene();
                scene.getStylesheets().clear();
                String css = getClass().getResource("/styles/LoginView.css").toExternalForm();
                scene.getStylesheets().add(css);
            }
        }
    });
}
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        // Get the user from login attempt
        User user = UserDao.loginAndGetUser(email, password);
        if (user != null) {
            App.user = user; // Set the logged in user
            showAlert("Succès", "Connexion réussie !", Alert.AlertType.INFORMATION);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
                Scene mainMenuScene = new Scene(loader.load());

                Scene currentScene = emailField.getScene();
                currentScene.getWindow().hide();

                Stage primaryStage = new Stage();
                primaryStage.setScene(mainMenuScene);
                primaryStage.setTitle("Menu Principal");
                primaryStage.show();
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors du chargement de l'interface principale.", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Identifiants incorrects.", Alert.AlertType.ERROR);
        }
    }
    @FXML
    private void handleSignup() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Signup.fxml"));
        Scene mainMenuScene = new Scene(loader.load());

       // Récupérer la fenêtre principale (Stage)
       Scene currentScene = emailField.getScene();
       currentScene.getWindow().hide();

       // Afficher la nouvelle fenêtre principale
       Stage primaryStage = new Stage();
       primaryStage.setScene(mainMenuScene);
       primaryStage.setTitle("Menu Principal");
       primaryStage.show();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
