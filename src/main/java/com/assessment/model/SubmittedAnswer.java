package com.assessment.model;

public class SubmittedAnswer {

    private int questionId;
    private String questionText;
    private String questionType;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctOption;
    private String selectedOption;
    private String answerText;

    private String answerStatus;
    private String adminComment;

    private String userName;
    private String userEmail;


    public SubmittedAnswer() {
    }


    public SubmittedAnswer(
            int questionId,
            String questionText,
            String questionType,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctOption,
            String selectedOption,
            String answerText,
            String answerStatus,
            String adminComment,
            String userName,
            String userEmail) {

        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;

        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;

        this.correctOption = correctOption;
        this.selectedOption = selectedOption;
        this.answerText = answerText;

        this.answerStatus = answerStatus;
        this.adminComment = adminComment;

        this.userName = userName;
        this.userEmail = userEmail;
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }


    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }


    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }


    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }


    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }


    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }


    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }


    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }


    public String getAnswerStatus() {
        return answerStatus;
    }

    public void setAnswerStatus(String answerStatus) {
        this.answerStatus = answerStatus;
    }


    public String getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(String adminComment) {
        this.adminComment = adminComment;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}