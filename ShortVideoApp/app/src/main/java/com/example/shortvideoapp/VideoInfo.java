package com.example.shortvideoapp;

public class VideoInfo {
    private String id;
    private String feedUrl;
    private String nickName;
    private String description;
    private String likeCount;
    private String avatar;

    public void setId(String id) {
        this.id = id;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return this.id;
    }

    public String getFeedUrl() {
        return this.feedUrl;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLikeCount() {
        return this.likeCount;
    }

    public String getAvatar() {
        return this.avatar;
    }
}
