package com.example.user;

public class Data {
    private String imageUrl, caption, key, name;

    private int like;

    public Data() {
    }

    public Data(String imageUrl, String caption, String key, String name) {
        this.imageUrl = imageUrl;
        this.caption = caption;
        this.key = key;
        this.name = name;
        this.like = 0;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}




