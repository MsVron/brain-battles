package com.quiz.database.dao;

import com.quiz.database.entity.Score;
import com.quiz.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDao {

    // Ajouter un score
    public static boolean addScore(Score score) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO userScores (userId, quizId, score, completedAt) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, score.getUserId());
            stmt.setInt(2, score.getQuizId());
            stmt.setInt(3, score.getScore());
            stmt.setTimestamp(4, new Timestamp(score.getCompletedAt().getTime()));
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer les scores d'un utilisateur
    public static List<Score> getScoresByUserId(int userId) {
        List<Score> scores = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM userScores WHERE userId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Score score = new Score();
                score.setScoreId(rs.getInt("scoreId"));
                score.setUserId(rs.getInt("userId"));
                score.setQuizId(rs.getInt("quizId"));
                score.setScore(rs.getInt("score"));
                score.setCompletedAt(rs.getTimestamp("completedAt"));
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    // Récupérer le classement des scores
    public static List<Score> getLeaderboard() {
        List<Score> leaderboard = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT userId, quizId, MAX(score) as score FROM userScores GROUP BY userId, quizId ORDER BY score DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Score score = new Score();
                score.setUserId(rs.getInt("userId"));
                score.setQuizId(rs.getInt("quizId"));
                score.setScore(rs.getInt("score"));
                leaderboard.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboard;
    }
}
