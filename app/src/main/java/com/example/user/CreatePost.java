package com.example.user;

import java.io.Serializable;

public class CreatePost implements Serializable {
    public String postId, eventName, eventLocation,eventVolunteers,eventCategory,eventManger,eventContact;

    public CreatePost(String postId, String eventName, String eventLocation) {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventVolunteers() {
        return eventVolunteers;
    }

    public void setEventVolunteers(String eventVolunteers) {
        this.eventVolunteers = eventVolunteers;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventManger() {
        return eventManger;
    }

    public void setEventManger(String eventManger) {
        this.eventManger = eventManger;
    }

    public String getEventContact() {
        return eventContact;
    }

    public void setEventContact(String eventContact) {
        this.eventContact = eventContact;
    }

    public CreatePost(String postId, String eventName, String eventLocation, String eventVolunteers, String eventCategory, String eventManger, String eventContact) {
        this.postId = postId;
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventVolunteers = eventVolunteers;
        this.eventCategory = eventCategory;
        this.eventManger = eventManger;
        this.eventContact = eventContact;
    }

    public CreatePost(){
    }
}
