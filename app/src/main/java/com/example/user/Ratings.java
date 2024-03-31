package com.example.user;

public class Ratings {

    private String userID, UserKey, name;
    private float rating;

    public Ratings() {
    }

    public Ratings(String userID, String userKey, String name, float rating) {
        this.userID = userID;
        UserKey = userKey;
        this.name = name;
        this.rating = rating;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}


