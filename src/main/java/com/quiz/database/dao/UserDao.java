package com.quiz.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.quiz.database.DatabaseConnection;
import com.quiz.utils.SecurityUtil;

public class UserDao {

    // Méthode pour l'authentification (login)
    public static boolean login(String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.printf("Email: %s%n", email);
            System.out.printf("Password: %s%n",  SecurityUtil.hashPassword(password));
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, SecurityUtil.hashPassword(password));
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // True si un utilisateur existe
        } catch (SQLException e) {
            System.out.printf("ERR getConnection");
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour créer un utilisateur
    public static boolean create(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Hacher le mot de passe avant de l'insérer
            String hashedPassword = SecurityUtil.hashPassword(password);

            // Préparer les paramètres de la requête
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, hashedPassword);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retourne true si l'utilisateur a été inséré avec succès
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de l'utilisateur : " + e.getMessage());
            return false;
        }
    }
    
}
