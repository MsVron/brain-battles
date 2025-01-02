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
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Check if score exists
            String select = "SELECT score FROM userScores WHERE userId = ? AND quizId = ?";
            PreparedStatement selectStmt = conn.prepareStatement(select);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, quizId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int existingScore = rs.getInt("score");
                if (score > existingScore) {
                    // Update if new score is higher
                    String update = "UPDATE userScores SET score = ? WHERE userId = ? AND quizId = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(update);
                    updateStmt.setInt(1, score);
                    updateStmt.setInt(2, userId);
                    updateStmt.setInt(3, quizId);
                    updateStmt.executeUpdate();
                }
            } else {
                // Insert new score
                String insert = "INSERT INTO userScores (userId, quizId, score) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insert);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, quizId);
                insertStmt.setInt(3, score);
                insertStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }
}
