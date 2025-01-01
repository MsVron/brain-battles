package com.quiz.database.entity;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;  // Mot de passe, à ne pas inclure dans l'objet retourné après la connexion

    // Constructeur avec paramètres
    public User(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;  // Garder le mot de passe pour le hachage
    }

    // Constructeur sans le mot de passe, utile après la connexion
    public User(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        // Mot de passe non défini après la connexion
    }

    // Getters et Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Méthode pour ne pas afficher le mot de passe (important pour la sécurité)
    @Override
    public String toString() {
        return "User{id=" + userId + ", username='" + username + "', email='" + email + "'}";
    }
}
