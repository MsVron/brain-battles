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




public class LoginController {
	
	@FXML
	public void initialize() {
	    emailField.sceneProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null) {
	            Stage stage = (Stage) emailField.getScene().getWindow();
	            stage.setWidth(900);
	            stage.setHeight(600);
	            stage.centerOnScreen();
	            
	            // Load CSS
	            Scene scene = emailField.getScene();
	            scene.getStylesheets().clear();  // Clear any existing stylesheets
	            String css = getClass().getResource("/styles/LoginView.css").toExternalForm();
	            scene.getStylesheets().add(css);
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

        // Appel à la DAO pour vérifier les informations
        boolean loginSuccess = UserDao.login(email, password);
        if (loginSuccess) {
            showAlert("Succès", "Connexion réussie !", Alert.AlertType.INFORMATION);

            try {
                // Charger l'interface principale (MainMenuView)
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
                 Scene mainMenuScene = new Scene(loader.load());

                // Récupérer la fenêtre principale (Stage)
                Scene currentScene = emailField.getScene();
                currentScene.getWindow().hide();

                // Afficher la nouvelle fenêtre principale
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
