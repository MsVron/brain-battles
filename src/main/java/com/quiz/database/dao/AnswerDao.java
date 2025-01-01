package com.quiz.database.dao;

import com.quiz.database.entity.Answer;
import com.quiz.database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDao {

    // Méthode pour récupérer les réponses d'une question donnée
    public static List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> answers = new ArrayList<>();
        String query = "SELECT * FROM answers WHERE questionId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int answerId = rs.getInt("answerId");
                String answerText = rs.getString("answerText");
                boolean isCorrect = rs.getBoolean("isCorrect");
                answers.add(new Answer(answerId, questionId, answerText, isCorrect));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }

    // Méthode pour ajouter une nouvelle réponse à une question
    public static boolean addAnswer(Answer answer) {
        String query = "INSERT INTO answers (questionId, answerText, isCorrect) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, answer.getQuestionId());
            stmt.setString(2, answer.getAnswerText());
            stmt.setBoolean(3, answer.isCorrect());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer une réponse par son ID
    public static boolean deleteAnswerById(int answerId) {
        String query = "DELETE FROM answers WHERE answerId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, answerId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour récupérer une réponse par son ID
    public static Answer getAnswerById(int answerId) {
        Answer answer = null;
        String query = "SELECT * FROM answers WHERE answerId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, answerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int questionId = rs.getInt("questionId");
                String answerText = rs.getString("answerText");
                boolean isCorrect = rs.getBoolean("isCorrect");
                answer = new Answer(answerId, questionId, answerText, isCorrect);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answer;
    }

      // Récupérer la réponse correcte d'une question par son ID
      public static Answer getCorrectAnswerByQuestionId(int questionId) {
        Answer correctAnswer = null;
        String query = "SELECT * FROM answers WHERE questionId = ? AND isCorrect = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Créer un objet Answer avec les informations récupérées
                correctAnswer = new Answer();
                correctAnswer.setAnswerId(rs.getInt("answerId"));
                correctAnswer.setQuestionId(rs.getInt("questionId"));
                correctAnswer.setAnswerText(rs.getString("answerText"));
                correctAnswer.setIsCorrect(rs.getBoolean("isCorrect"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correctAnswer;
    }
}
