package com.quiz.database.dao;

import com.quiz.database.DatabaseConnection;
import com.quiz.database.entity.PlayerScore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserScoreDao {

    // Méthode pour récupérer les scores depuis la base de données
    public static List<PlayerScore> getLeaderboard() {
        List<PlayerScore> leaderboard = new ArrayList<>();
        String query = "SELECT u.username, us.score FROM userScores us " +
                       "JOIN users u ON us.userId = u.userId " +
                       "ORDER BY us.score DESC LIMIT 100"; // Récupère les 100 meilleurs scores

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                // Ajout d'un nouveau PlayerScore à la liste
                leaderboard.add(new PlayerScore(username, score));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leaderboard;
    }
    
    public static boolean saveScore(int userId, int quizId, int score) {
        String query = "INSERT INTO userScores (userId, quizId, score) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            stmt.setInt(3, score);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
