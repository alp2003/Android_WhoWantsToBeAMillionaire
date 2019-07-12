package com.example.user.hitmillionaire11;

public class Question {

    private String AnswerA;
    private String AnswerB;
    private String AnswerC;
    private String AnswerD;
    private String CategoryID;
    private String CorrectAnswer;
    private String Question;
    private String isImageQuestion;
    private String language;

    public Question() {
    }

    public Question(String answerA, String answerB, String answerC, String answerD,
                    String categoryID, String correctAnswer,
                    String question, String isImageQuestion, String language) {
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        CategoryID = categoryID;
        CorrectAnswer = correctAnswer;
        Question = question;
        this.isImageQuestion = isImageQuestion;
        this.language = language;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getIsImageQuestion() {
        return isImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        this.isImageQuestion = isImageQuestion;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
