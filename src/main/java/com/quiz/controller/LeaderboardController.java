package com.quiz.controller;

import com.quiz.database.dao.UserScoreDao;
import com.quiz.database.entity.PlayerScore;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {

    @FXML
    private TableView<PlayerScore> leaderboardTable;

    @FXML
    private TableColumn<PlayerScore, Integer> rankColumn;

    @FXML
    private TableColumn<PlayerScore, String> nameColumn;

    @FXML
    private TableColumn<PlayerScore, Integer> scoreColumn;

    @FXML
    public void initialize() {
        List<PlayerScore> leaderboardDataList = UserScoreDao.getLeaderboard();
        ObservableList<PlayerScore> leaderboardData = FXCollections.observableArrayList(leaderboardDataList);
    
        // Affecter dynamiquement les rangs
        for (int i = 0; i < leaderboardData.size(); i++) {
            leaderboardData.get(i).setRank(i + 1); // Le rang commence à 1
        }
    
        rankColumn.setCellValueFactory(cellData -> cellData.getValue().rankProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
    
        leaderboardTable.setItems(leaderboardData);
    }    

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenuView.fxml"));
            Scene mainMenuScene = new Scene(loader.load());
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
