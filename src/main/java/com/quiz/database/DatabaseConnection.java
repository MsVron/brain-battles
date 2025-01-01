package com.quiz.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_PATH = "database/quiz.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_PATH;

    static {
        initializeDatabase();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private static void initializeDatabase() {
        System.out.println("initializeDatabase.....");
        try {
            // Vérifie si le fichier de base de données existe
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists()) {
                System.out.println("Base de données introuvable, création en cours...");
                dbFile.getParentFile().mkdirs(); // Crée le dossier 'database' si nécessaire

                // Crée une connexion et initialise la base avec le script SQL
                try (Connection conn = DriverManager.getConnection(DB_URL);
                     BufferedReader reader = new BufferedReader(
                         new InputStreamReader(DatabaseConnection.class.getResourceAsStream("/init.sql")));
                     Statement stmt = conn.createStatement()) {

                    StringBuilder sqlBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sqlBuilder.append(line).append("\n");
                    }

                    String[] sqlCommands = sqlBuilder.toString().split(";");
                    for (String command : sqlCommands) {
                        if (!command.trim().isEmpty()) {
                            stmt.execute(command.trim());
                        }
                    }

                    System.out.println("Base de données initialisée avec succès !");
                }
            } else {
                System.out.println("Base de données existante trouvée.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'initialisation de la base de données : " + e.getMessage());
        }
    }
}

