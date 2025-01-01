-- Création des tables
CREATE TABLE IF NOT EXISTS users (
    userId INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quiz (
    quizId INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    category TEXT NOT NULL,
    difficulty INTEGER NOT NULL,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS questions (
    questionId INTEGER PRIMARY KEY AUTOINCREMENT,
    quizId INTEGER,
    questionText TEXT NOT NULL,
    correctAnswer TEXT NOT NULL,
    points INTEGER DEFAULT 1,
    FOREIGN KEY (quizId) REFERENCES quiz(quizId)
);

CREATE TABLE IF NOT EXISTS answers (
    answerId INTEGER PRIMARY KEY AUTOINCREMENT,
    questionId INTEGER NOT NULL,
    answerText TEXT NOT NULL,
    isCorrect BOOLEAN NOT NULL,
    FOREIGN KEY (questionId) REFERENCES questions(questionId)
);


CREATE TABLE IF NOT EXISTS userScores (
    scoreId INTEGER PRIMARY KEY AUTOINCREMENT,
    userId INTEGER NOT NULL,
    quizId INTEGER NOT NULL,
    score INTEGER NOT NULL,
    completedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (quizId) REFERENCES quiz(quizId) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Ajouter des utilisateurs
INSERT INTO users (username, email, password) VALUES ('Test', 'test@quiz.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO users (username, email, password) VALUES ('Aya', 'aya@admin.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');
INSERT INTO users (username, email, password) VALUES ('Hamza', 'hamza@admin.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918');

-- Ajouter des quiz
INSERT INTO quiz (title, category, difficulty) VALUES ('History Quiz', 'History', 1);
INSERT INTO quiz (title, category, difficulty) VALUES ('Math Quiz', 'Mathematics', 2);
INSERT INTO quiz (title, category, difficulty) VALUES ('Science Quiz', 'Science', 2);
INSERT INTO quiz (title, category, difficulty) VALUES ('Geography Quiz', 'Geography', 1);
INSERT INTO quiz (title, category, difficulty) VALUES ('Literature Quiz', 'Literature', 3);

-- Ajouter des questions pour le quiz History Quiz (quizId = 1)
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (1, 'Who was the first president of the United States?', 'George Washington', 1);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (1, 'In what year did World War I begin?', '1914', 2);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (1, 'What empire was ruled by Julius Caesar?', 'Roman Empire', 1);

-- Ajouter des questions pour le quiz Math Quiz (quizId = 2)
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (2, 'What is 5 + 5?', '10', 1);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (2, 'What is the square root of 16?', '4', 2);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (2, 'What is 7 * 6?', '42', 1);

-- Ajouter des questions pour le quiz Science Quiz (quizId = 3)
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (3, 'What is the chemical symbol for water?', 'H2O', 1);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (3, 'What planet is known as the Red Planet?', 'Mars', 2);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (3, 'What is the hardest natural substance on Earth?', 'Diamond', 1);

-- Ajouter des questions pour le quiz Geography Quiz (quizId = 4)
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (4, 'What is the capital of Japan?', 'Tokyo', 1);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (4, 'Which ocean is the largest?', 'Pacific Ocean', 2);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (4, 'What country is the Eiffel Tower located in?', 'France', 1);

-- Ajouter des questions pour le quiz Literature Quiz (quizId = 5)
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (5, 'Who wrote "Romeo and Juliet"?', 'William Shakespeare', 1);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (5, 'What is the title of the first Harry Potter book?', 'Harry Potter and the Sorcerers Stone', 2);
INSERT INTO questions (quizId, questionText, correctAnswer, points) VALUES (5, 'Who wrote "Moby-Dick"?', 'Herman Melville', 1);

-- Réponses pour le quiz History Quiz (quizId = 1)
-- Question 1: Who was the first president of the United States?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (1, 'George Washington', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (1, 'Thomas Jefferson', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (1, 'Abraham Lincoln', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (1, 'John Adams', 0);

-- Question 2: In what year did World War I begin?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (2, '1914', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (2, '1910', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (2, '1920', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (2, '1899', 0);

-- Question 3: What empire was ruled by Julius Caesar?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (3, 'Roman Empire', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (3, 'Ottoman Empire', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (3, 'Mongol Empire', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (3, 'Byzantine Empire', 0);

-- Réponses pour le quiz Math Quiz (quizId = 2)
-- Question 1: What is 5 + 5?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (4, '10', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (4, '8', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (4, '12', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (4, '20', 0);

-- Question 2: What is the square root of 16?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (5, '4', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (5, '3', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (5, '8', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (5, '2', 0);

-- Question 3: What is 7 * 6?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (6, '42', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (6, '36', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (6, '48', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (6, '49', 0);

-- Réponses pour le quiz Science Quiz (quizId = 3)
-- Question 1: What is the chemical symbol for water?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (7, 'H2O', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (7, 'CO2', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (7, 'O2', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (7, 'H2', 0);

-- Question 2: What planet is known as the Red Planet?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (8, 'Mars', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (8, 'Venus', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (8, 'Jupiter', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (8, 'Saturn', 0);

-- Question 3: What is the hardest natural substance on Earth?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (9, 'Diamond', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (9, 'Gold', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (9, 'Iron', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (9, 'Emerald', 0);

-- Réponses pour le quiz Geography Quiz (quizId = 4)
-- Question 1: What is the capital of Japan?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (10, 'Tokyo', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (10, 'Seoul', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (10, 'Beijing', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (10, 'Bangkok', 0);

-- Question 2: Which ocean is the largest?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (11, 'Pacific Ocean', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (11, 'Atlantic Ocean', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (11, 'Indian Ocean', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (11, 'Arctic Ocean', 0);

-- Question 3: What country is the Eiffel Tower located in?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (12, 'France', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (12, 'Italy', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (12, 'Germany', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (12, 'Spain', 0);

-- Réponses pour le quiz Literature Quiz (quizId = 5)
-- Question 1: Who wrote "Romeo and Juliet"?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (13, 'William Shakespeare', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (13, 'Charles Dickens', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (13, 'Jane Austen', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (13, 'Mark Twain', 0);

-- Question 2: What is the title of the first Harry Potter book?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (14, 'Harry Potter and the Sorcerers Stone', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (14, 'Harry Potter and the Chamber of Secrets', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (14, 'Harry Potter and the Prisoner of Azkaban', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (14, 'Harry Potter and the Goblet of Fire', 0);

-- Question 3: Who wrote "Moby-Dick"?
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (15, 'Herman Melville', 1);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (15, 'H.G. Wells', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (15, 'Mark Twain', 0);
INSERT INTO answers (questionId, answerText, isCorrect) VALUES (15, 'Ernest Hemingway', 0);
-- Ajouter des scores
INSERT INTO userScores (userId, quizId, score) VALUES (1, 1, 1);
INSERT INTO userScores (userId, quizId, score) VALUES (2, 1, 2);


