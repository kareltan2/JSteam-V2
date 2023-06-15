package com.example.eatadvisory.deprecated.model;

/**
 * @author kareltan
 * @description:
 * deprecated due to migrate to real database connection. this is only support for JSteam-V1
 */
@Deprecated
public class Review {

    private String gameName;

    private String content;

    private String username;

    public Review(String gameName, String content, String username) {
        this.gameName = gameName;
        this.content = content;
        this.username = username;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
