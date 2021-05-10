package com.oxy.vertx.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Post {
    @JsonProperty("id")
    private int id;

    @JsonProperty("author_id")
    private int authorId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("content")
    private String content;

    @JsonProperty("date")
    private String date;

    public Post(int id, int authorId, String title, String description, String content, String date) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
