package com.quiz.controller;

import com.quiz.database.dao.QuestionDao;
import com.quiz.database.entity.User;
import com.quiz.App;
import com.quiz.database.dao.AnswerDao;
import com.quiz.database.dao.UserScoreDao;
import com.quiz.database.entity.Question;
import com.quiz.database.entity.Answer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

import java.util.List;

public class QuizController {

    @FXML private Label quizTitle;
    @FXML private Label questionText;
    @FXML private RadioButton option1;
    @FXML private RadioButton option2;
    @FXML private RadioButton option3;
    @FXML private RadioButton option4;
    @FXML private Label feedbackLabel;
    @FXML private Label timerLabel;
    @FXML private Button pauseButton;
    @FXML private Button musicButton;
    @FXML private Label questionNumberLabel;
    @FXML private Button settingsButton;

    private ToggleGroup answerGroup;
    private Timeline timer;
    private int currentQuestionIndex = 0;
    private List<Question> questions;
    private List<Answer> answers;
    private int score = 0;
    private int timeLeft = 30;
    private boolean isPaused = false;
    private boolean isMusicOn = false;
    private int quizId = 1;

    @FXML
    public void initialize() {
        questions = QuestionDao.getQuestionsByQuizId(quizId);
        setupAnswerGroup();
        initializeTimer();
        setupControlButtons();
        setupStyling();
        loadQuestion();
        updateQuestionNumber();
    }

    private void setupAnswerGroup() {
        answerGroup = new ToggleGroup();
        option1.setToggleGroup(answerGroup);
        option2.setToggleGroup(answerGroup);
        option3.setToggleGroup(answerGroup);
        option4.setToggleGroup(answerGroup);
    }

    private void initializeTimer() {
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!isPaused && timeLeft > 0) {
                timeLeft--;
                updateTimerDisplay();
                if (timeLeft == 0) {
                    processAnswer();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void updateTimerDisplay() {
        timerLabel.setText(timeLeft + "s");
    }

    private void setupControlButtons() {
        pauseButton.setOnAction(e -> handlePause());
        musicButton.setOnAction(e -> handleMusic());
        settingsButton.setOnAction(e -> handleSettings());
    }

    @FXML
    private void handlePause() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "▶" : "⏸");
        if (isPaused) {
            timer.pause();
        } else {
            timer.play();
        }
    }

    @FXML
    private void handleMusic() {
        isMusicOn = !isMusicOn;
        musicButton.setText(isMusicOn ? "🔊" : "🔇");
        // Music logic would go here
    }

    @FXML
    private void handleSettings() {
        // Settings logic would go here
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            displayQuestion(currentQuestion);
            resetQuestionState();
        } else {
            finishQuiz();
        }
    }

    private void displayQuestion(Question question) {
        questionText.setText(question.getQuestionText());
        answers = AnswerDao.getAnswersByQuestionId(question.getQuestionId());
        
        if (answers.size() == 4) {
            option1.setText(answers.get(0).getAnswerText());
            option2.setText(answers.get(1).getAnswerText());
            option3.setText(answers.get(2).getAnswerText());
            option4.setText(answers.get(3).getAnswerText());
        } else {
            handleErrorInAnswers();
        }
    }

    private void resetQuestionState() {
        timeLeft = 30;
        feedbackLabel.setText("");
        answerGroup.selectToggle(null);
        updateQuestionNumber();
        timer.play();
    }

    private void updateQuestionNumber() {
        questionNumberLabel.setText((currentQuestionIndex + 1) + "/" + questions.size());
    }

    private void handleErrorInAnswers() {
        feedbackLabel.setText("Erreur de chargement des réponses.");
        feedbackLabel.setStyle("-fx-text-fill: red;");
    }

    @FXML
    private void handleSubmitAnswer() {
        timer.pause();
        processAnswer();
    }

    private void processAnswer() {
        RadioButton selectedOption = (RadioButton) answerGroup.getSelectedToggle();
        Question currentQuestion = questions.get(currentQuestionIndex);
        
        if (selectedOption != null) {
            checkAnswer(selectedOption.getText(), currentQuestion);
        } else {
            handleNoAnswer();
        }

        moveToNextQuestion();
    }

    private void checkAnswer(String selectedAnswer, Question currentQuestion) {
        Answer correctAnswer = AnswerDao.getCorrectAnswerByQuestionId(currentQuestion.getQuestionId());
        
        if (selectedAnswer.equals(correctAnswer.getAnswerText())) {
            handleCorrectAnswer();
        } else {
            handleWrongAnswer();
        }
    }

    private void handleCorrectAnswer() {
        feedbackLabel.setText("Bonne réponse !");
        feedbackLabel.setStyle("-fx-text-fill: green;");
        score++;
    }

    private void handleWrongAnswer() {
        feedbackLabel.setText("Mauvaise réponse.");
        feedbackLabel.setStyle("-fx-text-fill: red;");
    }

    private void handleNoAnswer() {
        feedbackLabel.setText("Temps écoulé !");
        feedbackLabel.setStyle("-fx-text-fill: red;");
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            loadQuestion();
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        timer.stop();
        saveQuizResults();
        closeQuizWindow();
    }

    private void saveQuizResults() {
        boolean isSaved = UserScoreDao.saveScore(App.user.getUserId(), quizId, score);
        if (isSaved) {
            showAlert("Quiz terminé !", "Votre score a été enregistré : " + score + "/" + questions.size(), AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Il y a eu un problème lors de l'enregistrement de votre score.", AlertType.ERROR);
        }
    }

    private void closeQuizWindow() {
        Scene currentScene = quizTitle.getScene();
        currentScene.getWindow().hide();
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void setupStyling() {
        try {
            String css = getClass().getResource("/styles/QuizView.css").toExternalForm();
            Scene scene = quizTitle.getScene();
            if (scene != null) {
                scene.getStylesheets().add(css);
            }
        } catch (Exception e) {
            System.err.println("Failed to load CSS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}