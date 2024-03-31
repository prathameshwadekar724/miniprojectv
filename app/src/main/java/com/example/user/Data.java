package com.example.user;

public class Data {
    private String ImageUrl,PostName, Key, Name,Description;


    public Data() {
    }

    public Data(String imageUrl, String postName, String key, String name, String description) {
        ImageUrl = imageUrl;
        PostName = postName;
        Key = key;
        Name = name;
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPostName() {
        return PostName;
    }

    public void setPostName(String postName) {
        PostName = postName;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}




