package com.quiz.database.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayerScore {

    private final IntegerProperty rank;
    private final StringProperty name;
    private final IntegerProperty score;

    // Constructeur
    public PlayerScore(String name, int score) {
        this.rank = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    // Constructor for PlayerScore with rank
    public PlayerScore(int rank, String name, int score) {
        this.rank = new SimpleIntegerProperty(rank);
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    // Getters and setters for properties
    public IntegerProperty rankProperty() {
        return rank;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    // Getters
    public int getRank() {
        return rank.get();
    }

    public String getName() {
        return name.get();
    }

    public int getScore() {
        return score.get();
    }

    // Setters
    public void setRank(int rank) {
        this.rank.set(rank);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setScore(int score) {
        this.score.set(score);
    }
}
