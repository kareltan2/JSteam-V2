package com.example.traveladvisory.model.dao;

/**
 * @author kareltan
 * @description:
 * used for mapping DAO (database) data
 */
public class Wish {

    private Integer id;

    private Integer userId;

    private Integer placeId;

    public Wish(Integer id, Integer userId, Integer placeId) {
        this.id = id;
        this.userId = userId;
        this.placeId = placeId;
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

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }
}
