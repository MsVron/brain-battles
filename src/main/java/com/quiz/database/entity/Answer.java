package com.quiz.database.entity;

public class Answer {

    private int answerId;       // ID de la réponse
    private int questionId;     // ID de la question associée
    private String answerText;  // Texte de la réponse
    private boolean isCorrect;  // Indicateur si la réponse est correcte ou non
    public Answer(){

    }
    // Constructeur
    public Answer(int answerId, int questionId, String answerText, boolean isCorrect) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Constructeur sans `answerId` (lors de l'insertion de nouvelles réponses)
    public Answer(int questionId, String answerText, boolean isCorrect) {
        this.questionId = questionId;
        this.answerText = answerText;
        this.isCorrect = isCorrect;
    }

    // Getters et setters
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
