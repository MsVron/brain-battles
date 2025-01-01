package com.quiz.database.dao;

import com.quiz.database.entity.Quiz;
import com.quiz.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDao {

    // Récupérer tous les quiz
    public static List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM quiz";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quizId"));
                quiz.setTitle(rs.getString("title"));
                quiz.setCategory(rs.getString("category"));
                quiz.setDifficulty(rs.getInt("difficulty"));
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    // Ajouter un nouveau quiz
    public static boolean addQuiz(Quiz quiz) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO quiz (title, category, difficulty) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, quiz.getTitle());
            stmt.setString(2, quiz.getCategory());
            stmt.setInt(3, quiz.getDifficulty());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer un quiz par son ID
    public static Quiz getQuizById(int quizId) {
        Quiz quiz = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM quiz WHERE quizId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                quiz = new Quiz();
                quiz.setQuizId(rs.getInt("quizId"));
                quiz.setTitle(rs.getString("title"));
                quiz.setCategory(rs.getString("category"));
                quiz.setDifficulty(rs.getInt("difficulty"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    // Supprimer un quiz par son ID
    public static boolean deleteQuizById(int quizId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM quiz WHERE quizId = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quizId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
