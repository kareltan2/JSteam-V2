package com.example.traveladvisory.deprecated.model;

/**
 * @author kareltan
 * @description:
 * deprecated due to migrate to real database connection. this is only support for JSteam-V1
 */
@Deprecated
public class Game {

    private String name;

    private String genre;

    private Double rating;

    private Integer price;

    private String description;

    public Game(String name, String genre, Double rating, Integer price, String description) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
