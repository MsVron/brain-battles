package com.quiz.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import com.quiz.database.dao.UserDao;

public class SignupController {

    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Button signupButton;
    
    @FXML
    private Hyperlink backToLoginLink;

    // Méthode appelée lorsque l'utilisateur clique sur "Créer le compte"
    @FXML
    private void handleSignup(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validation des champs
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(AlertType.ERROR, "Erreur", "Les mots de passe ne correspondent pas.");
            return;
        }

        if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Erreur", "L'email n'est pas valide.");
            return;
        }

        // Insérer l'utilisateur dans la base de données
        if (UserDao.create(name, email, password)) {
            showAlert(AlertType.INFORMATION, "Succès", "Compte créé avec succès !");
            try{
                goToLogin();
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                stage.close();
            }catch(IOException e){
            }
            
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la création du compte.");
        }
    }

    // Méthode pour valider un email (simplifiée pour l'exemple)
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Méthode pour afficher une alerte
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode appelée lorsque l'utilisateur clique sur "Se connecter" (Retour à la page de connexion)
    @FXML
    private void goToLogin() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent loginRoot = loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(loginRoot));
            loginStage.setTitle("Connexion");
            loginStage.show();

    }
}



