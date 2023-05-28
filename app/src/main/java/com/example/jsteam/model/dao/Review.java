package com.example.jsteam.model.dao;

/**
 * @author kareltan
 * @description:
 * used for mapping DAO (database) data
 */
public class Review {

    private Integer id;

    private Integer userId;

    private Integer gameId;

    private String comment;

    public Review(Integer id, Integer userId, Integer gameId, String comment) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
