package com.quiz.database.dao;

import com.quiz.database.entity.Question;
import com.quiz.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    // Méthode pour récupérer les questions d'un quiz donné par son ID
    public static List<Question> getQuestionsByQuizId(int quizId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions WHERE quizId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("questionId");
                String questionText = rs.getString("questionText");
                String correctAnswer = rs.getString("correctAnswer");
                int points = rs.getInt("points");
                questions.add(new Question(questionId, quizId, questionText, correctAnswer, points));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    // Méthode pour ajouter une nouvelle question dans la base de données
    public static boolean addQuestion(Question question) {
        String query = "INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, question.getQuizId());
            stmt.setString(2, question.getQuestionText());
            stmt.setString(3, question.getCorrectAnswer());
            stmt.setInt(4, question.getPoints());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer une question par son ID
    public static boolean deleteQuestionById(int questionId) {
        String query = "DELETE FROM questions WHERE questionId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, questionId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer une question spécifique par son ID
    public static Question getQuestionById(int questionId) {
        Question question = null;
        String query = "SELECT * FROM questions WHERE questionId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int quizId = rs.getInt("quizId");
                String questionText = rs.getString("questionText");
                String correctAnswer = rs.getString("correctAnswer");
                int points = rs.getInt("points");
                question = new Question(questionId, quizId, questionText, correctAnswer, points);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return question;
    }
}
