package com.quiz.controller;

import com.quiz.database.dao.QuestionDao;
import com.quiz.database.entity.User;
import com.quiz.App;
import com.quiz.database.dao.AnswerDao;
import com.quiz.database.dao.UserScoreDao;
import com.quiz.database.entity.Question;
import com.quiz.database.entity.Answer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.util.List;

public class QuizController {

    @FXML
    private Label quizTitle;

    @FXML
    private Label questionText;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private RadioButton option4;

    @FXML
    private Label feedbackLabel;

    private ToggleGroup answerGroup;

    private int currentQuestionIndex = 0;
    private List<Question> questions;  // Liste des questions du quiz
    private List<Answer> answers;      // Liste des réponses possibles pour chaque question
    private int score = 0;             // Score de l'utilisateur

    private int quizId = 1; // Identifiant du quiz (à définir selon le quiz en cours)

    @FXML
    public void initialize() {
        // Charger les questions depuis la base de données
        questions = QuestionDao.getQuestionsByQuizId(quizId);

        // Initialiser les boutons Radio
        answerGroup = new ToggleGroup();
        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);

        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionText.setText(currentQuestion.getQuestionText());

            // Charger les réponses possibles pour la question actuelle
            answers = AnswerDao.getAnswersByQuestionId(currentQuestion.getQuestionId());

            // Assurez-vous que la liste des réponses contient bien 4 options
            if (answers.size() == 4) {
                option1.setText(answers.get(0).getAnswerText());
                option2.setText(answers.get(1).getAnswerText());
                option3.setText(answers.get(2).getAnswerText());
                option4.setText(answers.get(3).getAnswerText());
                feedbackLabel.setText("");
            } else {
                // Gestion des erreurs si la taille de la liste des réponses est incorrecte
                feedbackLabel.setText("Erreur de chargement des réponses.");
                feedbackLabel.setStyle("-fx-text-fill: red;");
            }
        }
    }

    @FXML
    private void handleSubmitAnswer() {
        RadioButton selectedOption = (RadioButton) answerGroup.getSelectedToggle();

        if (selectedOption == null) {
            feedbackLabel.setText("Veuillez sélectionner une réponse.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String selectedAnswer = selectedOption.getText();
        Question currentQuestion = questions.get(currentQuestionIndex);
        Answer correctAnswer = AnswerDao.getCorrectAnswerByQuestionId(currentQuestion.getQuestionId());

        // Vérification si la réponse sélectionnée est correcte
        if (selectedAnswer.equals(correctAnswer.getAnswerText())) {
            feedbackLabel.setText("Bonne réponse !");
            feedbackLabel.setStyle("-fx-text-fill: green;");
            score++;  // Incrémenter le score si la réponse est correcte
        } else {
            feedbackLabel.setText("Mauvaise réponse.");
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion();
        } else {
            // À la fin du quiz, sauvegarder le score
            boolean isSaved = UserScoreDao.saveScore(App.user.getUserId(), quizId, score);
            if (isSaved) {
                showAlert("Quiz terminé !", "Votre score a été enregistré : " + score, AlertType.INFORMATION);
            } else {
                showAlert("Erreur", "Il y a eu un problème lors de l'enregistrement de votre score.", AlertType.ERROR);
            }

            // Fermer le quiz
            Scene currentScene = quizTitle.getScene();
            currentScene.getWindow().hide();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
