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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
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
    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

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
        // Set up button icons using Unicode characters
        pauseButton.setText("⏸");
        musicButton.setText("🔇");
        settingsButton.setText("⚙");
        
        pauseButton.getStyleClass().add("control-button");
        musicButton.getStyleClass().add("control-button");
        settingsButton.getStyleClass().add("control-button");

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
        musicButton.setStyle(isMusicOn ? "-fx-text-fill: #3B82F6;" : "-fx-text-fill: black;");
        // Add music playback logic here
    }

    @FXML
    private void handleSettings() {
        try {
            // Pause the quiz before showing settings
            boolean wasRunning = !isPaused;
            if (wasRunning) {
                handlePause(); // This will pause the timer and update the pause button
            }

            // Load the options menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OptionsMenu.fxml"));
            VBox optionsDialog = (VBox) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Options");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(quizTitle.getScene().getWindow());
            
            Scene scene = new Scene(optionsDialog);
            scene.getStylesheets().add(getClass().getResource("/styles/QuizView.css").toExternalForm());
            
            dialogStage.setScene(scene);

            OptionsMenuController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setQuizController(this);

            // Show dialog and handle resuming when closed
            dialogStage.showAndWait();
            
            // Only resume if the quiz was running before opening settings
            // and if we haven't started a new game or quit
            if (wasRunning && quizTitle.getScene() != null && quizTitle.getScene().getWindow().isShowing()) {
                handlePause(); // This will unpause the timer and update the pause button
            }

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load options menu.", AlertType.ERROR);
        }
    }
  
  
    public void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        timeLeft = 30;
        isPaused = false;
        loadQuestion();
        if (timer != null) {
            timer.play();
        }
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
            feedbackLabel.setText("");
        } else {
            handleErrorInAnswers();
        }
    }

    private void resetQuestionState() {
        timeLeft = 30;
        updateTimerDisplay();
        feedbackLabel.setText("");
        answerGroup.selectToggle(null);
        updateQuestionNumber();
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
        if (!isPaused) {
            timer.pause();
            processAnswer();
        }
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
        if (timer != null) {
            timer.stop();
        }
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

    public void closeQuizWindow() {
        if (timer != null) {
            timer.stop();
        }
        Scene currentScene = quizTitle.getScene();
        if (currentScene != null) {
            currentScene.getWindow().hide();
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

   private void setupStyling() {
    try {
        // Update the CSS path
        String css = getClass().getResource("/styles/QuizView.css").toExternalForm(); // Changed path
        Scene scene = quizTitle.getScene();
        if (scene != null) {
            scene.getStylesheets().add(css);
        }
    } catch (Exception e) {
        System.err.println("Failed to load CSS: " + e.getMessage());
        e.printStackTrace();
    }
}
    // Method for cleanup when closing
    public void cleanup() {
        if (timer != null) {
            timer.stop();
        }
    }
}