package com.quiz;

import java.net.URL;
import com.quiz.database.entity.User;
import java.sql.SQLException;

import com.quiz.database.DatabaseConnection;

import javafx.application.Application;
// import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {
	public static User user;

    public static void main(String[] args) {
        JavaFXQuizApp.main(args);
    }

    public static class JavaFXQuizApp extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            // loader
            System.out.println("APP RUNING...");
            URL fxmlPath = getClass().getResource("/fxml/LoginView.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlPath);

            try {
            // Calling getConnection will trigger the static block and initialize the database
            DatabaseConnection.getConnection();
            // You can now use the connection to interact with your database
            System.out.println("Database connection established!");

            // Add your application logic here
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            
            stage.setTitle("Quiz Application");
            stage.setScene(new Scene(loader.load()));
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

}
