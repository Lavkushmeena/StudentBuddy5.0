package com.nitukbt19.StudentBuddy.Models;

public class TeacherList {
    private String userID;
    private String userName;

    public TeacherList() {
    }

    public TeacherList(String userID, String userName) {
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
