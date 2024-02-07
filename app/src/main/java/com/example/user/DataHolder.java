package com.example.user;

public class DataHolder {
    private static CreatePost selectedPost;

    public static CreatePost getSelectedPost() {
        return selectedPost;
    }

    public static void setSelectedPost(CreatePost selectedPost) {
        DataHolder.selectedPost = selectedPost;
    }
}