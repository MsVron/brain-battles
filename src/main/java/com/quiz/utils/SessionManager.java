package com.quiz.utils;

public class SessionManager {

    private static SessionManager instance;
    private String loggedInUser;
    private int loggedInUserId;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setLoggedInUser(String username, int userId) {
        this.loggedInUser = username;
        this.loggedInUserId = userId;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public int getLoggedInUserId() {
        return loggedInUserId;
    }

    public void clearSession() {
        this.loggedInUser = null;
        this.loggedInUserId = 0;
    }
}
