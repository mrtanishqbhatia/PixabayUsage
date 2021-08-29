package com.pixabayusage.models;

import android.text.TextUtils;

public class Image {
    private final int likes;
    private final String tags;
    private final long views;
    private final int comments;
    private final long downloads;
    private final String webformatURL;
    private final String user;

    public Image(int likes, String tags, long views, int comments, long downloads, String webformatURL, String user) {
        this.likes = likes;
        this.tags = tags;
        this.views = views;
        this.comments = comments;
        this.downloads = downloads;
        this.webformatURL = webformatURL;
        this.user = user;
    }

    public String getLikes() {
        return String.valueOf(likes);
    }

    public String getTags() {
        if (tags == null) return "";
        if (tags.contains(", ")) {
            String[] splitTags = tags.toUpperCase().split(", ");
            return TextUtils.join(" - ", splitTags);
        } else return tags;
    }

    public long getViews() {
        return views;
    }

    public String getComments() {
        return String.valueOf(comments);
    }

    public long getDownloads() {
        return downloads;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public String getUser() {
        return user;
    }

}