package com.nitukbt19.StudentBuddy.Models;

public class StudentList {
    private String userID;
    private String userName;

    public StudentList() {
    }

    public StudentList(String userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userName) {
        this.userID = userID;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
