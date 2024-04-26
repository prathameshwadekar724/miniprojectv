package com.example.user;

public class Average {
    private String name;
    private float average;

    public Average() {
    }

    public Average(String name, float average) {
        this.name = name;
        this.average = average;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
