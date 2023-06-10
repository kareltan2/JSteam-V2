package com.example.eatadvisory.model.dao;

/**
 * @author kareltan
 * @description:
 * used for mapping DAO (database) data
 */
public class Review {

    private Integer id;

    private Integer userId;

    private Integer foodId;

    private String comment;

    public Review(Integer id, Integer userId, Integer gameId, String comment) {
        this.id = id;
        this.userId = userId;
        this.foodId = gameId;
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

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer gameId) {
        this.foodId = gameId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
