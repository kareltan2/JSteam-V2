package com.example.traveladvisory.model.dao;

/**
 * @author kareltan
 * @description:
 * used for mapping DAO (database) data
 */
public class Place {

    private Integer id;

    private String name;

    private String genre;

    private Double rating;

    private String price;

    private String image;

    private String description;

    public Place(Integer id, String name, String genre, Double rating, String price, String image, String description) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

