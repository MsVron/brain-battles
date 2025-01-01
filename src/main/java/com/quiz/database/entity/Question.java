package com.quiz.database.entity;

public class Question {

    private int questionId;        // ID de la question
    private int quizId;            // ID du quiz auquel la question appartient
    private String questionText;   // Texte de la question
    private String correctAnswer;  // Réponse correcte
    private int points;            // Points associés à la question

    // Constructeur
    public Question(int questionId, int quizId, String questionText, String correctAnswer, int points) {
        this.questionId = questionId;
        this.quizId = quizId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    // Constructeur sans questionId (pour l'insertion de nouvelles questions)
    public Question(int quizId, String questionText, String correctAnswer, int points) {
        this.quizId = quizId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    // Getters et setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", quizId=" + quizId +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", points=" + points +
                '}';
    }
}
