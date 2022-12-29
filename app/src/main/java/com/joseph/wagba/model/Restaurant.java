package com.joseph.wagba.model;



public class Restaurant  {

    String name;
    Long rating;
    String imageURL;
    String address;
    String category;



    public Restaurant(String name, Long rating, String imagePos, String address, String category) {
        this.name = name;
        this.rating = rating;
        this.imageURL = imagePos;
        this.address = address;
        this.category = category;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
